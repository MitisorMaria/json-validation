package com.task.jsonvalidator.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.networknt.schema.JsonSchemaException;
import com.task.jsonvalidator.controller.paramvalidator.FilePathConstraint;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.util.Constants;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.error(response.getBody().getMessage(), exception);
        return response;
    }

    @ExceptionHandler(JsonParseException.class)
    protected ResponseEntity<Response> handleJsonParseException(JsonParseException exception) {
        log.error(Constants.ERROR_INVALID_JSON, exception);
        return new ResponseEntity<>(new Response(Constants.ERROR_INVALID_JSON, false), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonSchemaException.class)
    protected ResponseEntity<Response> handleJsonSchemaException(JsonSchemaException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new Response(Constants.ERROR_INVALID_JSON_SCHEMA + exception.getMessage(), false),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response> handleException(final Exception exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(new Response(exception.getMessage(), false), HttpStatus.BAD_REQUEST);
    }
}