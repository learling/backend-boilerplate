package de.boilerplate.springbootbackend.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String value) {
        super("Not found: " + value);
    }
}
