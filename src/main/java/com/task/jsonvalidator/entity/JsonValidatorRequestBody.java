package com.task.jsonvalidator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * This type of objects is sent as a request body for the validation request. It contains the JSON object to be validated,
 * and the path to the user-given schema used for the validation.
 */
@AllArgsConstructor
@NoArgsConstructor
public class JsonValidatorRequestBody {
    @Getter
    private String schemaSource;
    @Getter
    private Object jsonObject;
}