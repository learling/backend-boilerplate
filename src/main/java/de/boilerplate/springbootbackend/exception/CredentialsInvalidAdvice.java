package de.boilerplate.springbootbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CredentialsInvalidAdvice {

    @ResponseBody
    @ExceptionHandler(CredentialsInvalidException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String passwordInvalidHandler(CredentialsInvalidException ex) {
        return ex.getMessage();
    }
}
