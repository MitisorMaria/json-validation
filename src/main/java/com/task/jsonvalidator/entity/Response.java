package com.task.jsonvalidator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Response object containing information regarding the validity of the JSON object, both in text message and in boolean
 * form.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    @Getter
    private String message;
    @Getter
    private boolean isValid;
}