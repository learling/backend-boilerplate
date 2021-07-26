package de.boilerplate.springbootbackend.exception;

public class PropertyNotUpdatableException extends RuntimeException {

    public PropertyNotUpdatableException(String attribute) {
        super("Not updatable: " + attribute);
    }
}
