package com.task.jsonvalidator.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.schema.JsonSchema;
import com.task.jsonvalidator.entity.Response;

/**
 * Handler for the JSON validation operation.
 */
public interface ValidationHandler {

    /**
     * Validates a given JSON object against a schema stored at a given path.
     *
     * @param jsonObject the JSON string to be validated
     * @param schema the schema used for the validation
     * @return a {@code Response} object containing information regarding the validity of the JSON object
     */
    Response validateJson(final JsonSchema schema, final String jsonObject) throws JsonProcessingException;
}