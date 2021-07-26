package de.boilerplate.springbootbackend.exception;

public class CredentialsInvalidException extends RuntimeException {

    public CredentialsInvalidException(String credential) {
        super("Credentials invalid: " + credential);
    }
}
