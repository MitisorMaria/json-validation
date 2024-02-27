package com.task.jsonvalidator.handler;

import com.task.jsonvalidator.entity.JsonValidatorRequestBody;
import com.task.jsonvalidator.entity.Response;

/**
 * Handler for the JSON validation operation.
 */
public interface ValidationHandler {

    /**
     * Validates a given JSON object against a schema stored at a given path.
     *
     * @param jsonValidatorRequestBody object that encapsulates the JSON object to be validated,
     * together with the path to the schema
     * @return a {@code Response} object containing information regarding the validity of the JSON object
     */
    public Response validateJson(JsonValidatorRequestBody jsonValidatorRequestBody);
}