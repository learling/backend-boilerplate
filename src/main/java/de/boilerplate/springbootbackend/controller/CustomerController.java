package de.boilerplate.springbootbackend.controller;

import de.boilerplate.springbootbackend.data.customer.*;
import de.boilerplate.springbootbackend.data.customer.token.CustomerToken;
import de.boilerplate.springbootbackend.data.customer.token.TokenEntity;
import de.boilerplate.springbootbackend.data.customer.token.TokenRepository;
import de.boilerplate.springbootbackend.exception.CredentialsInvalidException;
import de.boilerplate.springbootbackend.exception.EntityNotFoundException;
import de.boilerplate.springbootbackend.exception.PropertyNotUpdatableException;
import de.boilerplate.springbootbackend.exception.UserExistsException;
import de.boilerplate.springbootbackend.global.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "https://main.d26991zspaafgq.amplifyapp.com/", "https://reactstrap-ts.netlify.app"})
@RestController
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${mail.from}")
    private String from;

    @Value("${origin.url}")
    private String origin;

    private void sendHtmlEmail(String to, String subject, String body) throws MessagingException {
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(to);
        helper.setBcc(from);
        helper.setFrom(from);
        helper.setSubject(subject);
        helper.setText(body, true);
        javaMailSender.send(msg);
    }

    @PostMapping("/customers")
    public Customer registerCustomer(@RequestBody CustomerEntity newCustomer) {
        String username = newCustomer.getUsername();
        String email = newCustomer.getEmail();
        String password = newCustomer.getPassword();
        if (username == null || email == null || password == null) {
            throw new CredentialsInvalidException("null");
        }
        if (customerRepository.findByUsername(username).isPresent()) {
            throw new UserExistsException(username);
        }
        if (customerRepository.findByEmail(email).isPresent()) {
            throw new UserExistsException(email);
        }
        newCustomer.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        CustomerEntity customerEntity = customerRepository.save(newCustomer);
        sendVerificationEmail(customerEntity, "Activation", false);
        return new Customer(customerEntity);
    }

    private void sendVerificationEmail(CustomerEntity customerEntity, String subject, boolean passwordReset) {
        TokenEntity tokenEntity = tokenRepository.save(new TokenEntity(customerEntity.getId()));
        String link = origin + "/customers/" + customerEntity.getId() + "/verify/" + tokenEntity.getToken();
        link += passwordReset ? "?reset=1" : "?active=1";
        String message = "<h1>Email verification</h1><p>Hello " + customerEntity.getUsername()
                + "</p><p>Please follow this temporary <a href=\"" + link + "\">link</a></p>"
                + "<p>Best regards<br/>" + from + "</p>";
        try {
            sendHtmlEmail(customerEntity.getEmail(), subject, message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/customers/login")
    public CustomerToken requestToken(@RequestBody CustomerLogin customerLogin) {
        String username = customerLogin.getUsername();
        CustomerEntity customerEntity = customerRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("username " + username));
        if (customerEntity.getActive() != 1) {
            sendVerificationEmail(customerEntity, "Activation", false);
            throw new CredentialsInvalidException("activation-email sent");
        }
        String enteredPassword = customerLogin.getPassword();
        String storedHash = customerEntity.getPassword();
        if (null == storedHash || !storedHash.startsWith("$2a$")) {
            throw new CredentialsInvalidException("stored hash");
        } else if (BCrypt.checkpw(enteredPassword, storedHash)) {
            TokenEntity tokenEntity = tokenRepository.save(new TokenEntity(customerEntity.getId()));
            return new CustomerToken(new Customer(customerEntity), tokenEntity.getToken());
        } else {
            throw new CredentialsInvalidException(username);
        }
    }

    @GetMapping("/customers/{customerId}/verify/{token}")
    public Customer verifyCustomerToken(@PathVariable Long customerId, @PathVariable String token, @RequestParam(required = false) String active, @RequestParam(required = false) String reset) {
        TokenEntity tokenEntity = tokenRepository.findByToken(token).orElseThrow(() -> new EntityNotFoundException("token " + token));
        if (LocalDateTime.now().isAfter(tokenEntity.getExpires())) {
            throw new CredentialsInvalidException("token expired " + tokenEntity.getExpires());
        }
        Long id = tokenEntity.getCustomerId();
        if (!id.equals(customerId)) {
            throw new EntityNotFoundException("customer id " + customerId);
        }
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("customer id " + id));
        if (active != null || reset != null) {
            updateValue(customerEntity, "active", active == null ? "0" : active, reset != null);
            customerEntity.setUpdated(LocalDateTime.now());
            customerEntity = customerRepository.save(customerEntity);
        }
        return new Customer(customerEntity);
    }

    @PatchMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Map<String, String> partialUpdate, @RequestParam(required = false) String reset) {
        CustomerEntity customerEntity = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("customer id " + String.valueOf(id)));
        partialUpdate.forEach((key, value) -> {
            updateValue(customerEntity, key, value, reset != null);
        });
        customerEntity.setCreated(customerEntity.getCreated());
        customerEntity.setUpdated(LocalDateTime.now());
        return new Customer(customerRepository.save(customerEntity));
    }

    private void updateValue(CustomerEntity customerEntity, String key, String value, boolean passwordReset) {
        switch (key) {
            case "username":
                if (customerRepository.findByUsername(value).isPresent()) {
                    throw new UserExistsException(value);
                }
                customerEntity.setUsername(value);
                break;
            case "active":
                customerEntity.setActive(value.equals("1") ? 1 : 0);
                break;
            case "email":
                if (customerRepository.findByEmail(value).isPresent() && !passwordReset) {
                    throw new UserExistsException(value);
                }
                customerEntity.setEmail(value);
                lockAndEmail(customerEntity, passwordReset);
                break;
            case "password":
                customerEntity.setPassword(BCrypt.hashpw(value, BCrypt.gensalt()));
                lockAndEmail(customerEntity, passwordReset);
                break;
            default:
                throw new PropertyNotUpdatableException(key);
        }
    }

    private void lockAndEmail(CustomerEntity customerEntity, boolean passwordReset) {
        updateValue(customerEntity, "active", "0", passwordReset);
        customerEntity.setUpdated(LocalDateTime.now());
        customerEntity = customerRepository.save(customerEntity);
        sendVerificationEmail(customerEntity, passwordReset ? "Reset password" : "Account update", passwordReset);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Long> deleteCustomer(@PathVariable Long id) {
        try {
            CustomerEntity customer = customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
            // TODO delete tokens
            customerRepository.delete(customer);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/customers")
    public Page<CustomerEntity> getCustomers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 50) Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @GetMapping("/customers/{id}")
    public CustomerEntity getCustomers(@PathVariable Long id) {
        return customerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @GetMapping("/customers/{attribute}/{value}")
    public Page<CustomerEntity> searchCustomers(@PathVariable String attribute, @PathVariable String value,
                                        @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 50) Pageable pageable) {
        try {
            return customerRepository.findAll(new CustomerSearch(attribute, value, Operator.LIKE), pageable);
        } catch (Exception e) {
            return customerRepository.findAll(new CustomerSearch(attribute, value, Operator.EQUAL), pageable);
        }
    }
}
