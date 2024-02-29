package com.task.jsonvalidator.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.task.jsonvalidator.controller.paramvalidator.FilePathConstraint;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.util.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


/**
 * Provides an exception handling mechanism for the entire application.
 */
@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Response> handleConstraintViolation(final ConstraintViolationException exception) {
        final ConstraintViolation<?> constraintViolation = exception.getConstraintViolations().iterator().next();
        ResponseEntity<Response> response;
        if (constraintViolation.getConstraintDescriptor().getAnnotation().annotationType()
                .equals(FilePathConstraint.class)) {
            response = new ResponseEntity<>(new Response(Constants.WRONG_FILE_NAME, false), HttpStatus.BAD_REQUEST);
        } else {
            response = new ResponseEntity<>(new Response(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<Response> handleJsonParseException() {
        return new ResponseEntity<>(new Response(Constants.ERROR_INVALID_JSON, false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> handleException(final Exception exception) {
        return new ResponseEntity<>(new Response(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}