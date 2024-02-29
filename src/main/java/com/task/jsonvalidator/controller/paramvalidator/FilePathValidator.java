package com.task.jsonvalidator.controller.paramvalidator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static com.task.jsonvalidator.util.Constants.getInvalidCharsByOS;


/**
 * Validator for the user-given schema name.
 */
public class FilePathValidator implements ConstraintValidator<FilePathConstraint, String> {

    @Override
    public boolean isValid(final String filename, final ConstraintValidatorContext context) {
        if (filename == null || filename.isEmpty() || filename.length() > 255) {
            return false;
        }
        return Arrays.stream(getInvalidCharsByOS()).noneMatch(ch -> filename.contains(ch.toString()));
    }
}