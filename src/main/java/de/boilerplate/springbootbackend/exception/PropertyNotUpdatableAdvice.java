package de.boilerplate.springbootbackend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class PropertyNotUpdatableAdvice {

    @ResponseBody
    @ExceptionHandler(PropertyNotUpdatableException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    String propertyNotUpdatableException(PropertyNotUpdatableException ex) {
        return ex.getMessage();
    }
}
