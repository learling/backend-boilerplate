package de.boilerplate.springbootbackend.data.customer.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<TokenEntity, Long>, JpaSpecificationExecutor<TokenEntity> {

    Optional<TokenEntity> findByToken(String token);
}
