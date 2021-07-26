package de.boilerplate.springbootbackend.exception;

public class UserExistsException extends RuntimeException {

    public UserExistsException(String user) {
        super("User exists: " + user);
    }
}
