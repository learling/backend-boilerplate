package de.boilerplate.springbootbackend.controller;

import de.boilerplate.springbootbackend.data.apiuser.*;
import de.boilerplate.springbootbackend.exception.CredentialsInvalidException;
import de.boilerplate.springbootbackend.exception.PropertyNotUpdatableException;
import de.boilerplate.springbootbackend.exception.UserExistsException;
import de.boilerplate.springbootbackend.exception.EntityNotFoundException;
import de.boilerplate.springbootbackend.global.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = {"http://localhost:3000", "https://main.d26991zspaafgq.amplifyapp.com/", "https://reactstrap-ts.netlify.app"})
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public Page<UserEntity> getUsers(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 50) Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @GetMapping("/users/{id}")
    public UserEntity getUser(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
    }

    @GetMapping("/users/{attribute}/{value}")
    public Page<UserEntity> searchUsers(@PathVariable String attribute, @PathVariable String value,
                                        @PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 50) Pageable pageable) {
        try {
            return userRepository.findAll(new UserSearch(attribute, value, Operator.LIKE), pageable);
        } catch (Exception e) {
            return userRepository.findAll(new UserSearch(attribute, value, Operator.EQUAL), pageable);
        }
    }

    @PatchMapping("/users/{id}")
    public UserEntity updateUser(@PathVariable Long id, @RequestBody Map<String, Object> partialUpdate) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
        partialUpdate.forEach((k, v) -> {switch (k) {
            // TODO email
            case "username":
                user.setUsername((String) v);
            case "email":
                user.setEmail((String) v);
            case "password":
                user.setPassword((String) v);
                break;
            default:
                throw new PropertyNotUpdatableException(k);
        }
        });
        return userRepository.save(user);
    }

    @PostMapping("/users")
    public UserEntity createUser(@RequestBody UserEntity newUser) {
        String username = newUser.getUsername();
        String email = newUser.getEmail();
        String password = newUser.getPassword();
        if (username == null || email == null || password == null) {
            throw new CredentialsInvalidException("null");
        }
        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserExistsException(username);
        }
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserExistsException(email);
        }
        if (newUser.getRoles() == null) {
            newUser.setRoles("ROLE_USER");
        }
        newUser.setPassword(newUser.getPassword());
        // TODO activate
        return userRepository.save(newUser);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        try {
            UserEntity user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.valueOf(id)));
            userRepository.delete(user);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
