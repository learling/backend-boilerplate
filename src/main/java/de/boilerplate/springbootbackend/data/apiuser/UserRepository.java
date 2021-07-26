package de.boilerplate.springbootbackend.data.apiuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

        Optional<UserEntity> findByUsername(String username);
        Optional<UserEntity> findByEmail(String email);
}
