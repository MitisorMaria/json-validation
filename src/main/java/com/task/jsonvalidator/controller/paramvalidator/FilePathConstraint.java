package com.task.jsonvalidator.controller.paramvalidator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Defines a constraint for validating the schema name.
 */
@Constraint(validatedBy = { FilePathValidator.class })
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER })
public @interface FilePathConstraint {

    String message() default "Invalid file name.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}