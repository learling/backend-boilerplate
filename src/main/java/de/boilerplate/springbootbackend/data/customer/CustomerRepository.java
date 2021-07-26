package de.boilerplate.springbootbackend.data.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>, JpaSpecificationExecutor<CustomerEntity> {

        Optional<CustomerEntity> findByUsername(String username);
        Optional<CustomerEntity> findByEmail(String email);
}
