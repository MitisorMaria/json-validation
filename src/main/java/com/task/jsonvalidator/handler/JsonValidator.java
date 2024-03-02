package com.task.jsonvalidator.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Class used for performing the validation of a JSON object against a given schema.
 */
@Component
class JsonValidator {

    /**
     * Validates a JSON object against a given schema.
     *
     * @param jsonObject the JSON object to be validated
     * @param jsonSchema the schema used
     * @return a set containing the validation error messages. If the object is valid, the set is empty.
     */
    Set<ValidationMessage> validate(final JsonNode jsonObject, final JsonSchema jsonSchema) {
        return jsonSchema.validate(jsonObject);
    }
}