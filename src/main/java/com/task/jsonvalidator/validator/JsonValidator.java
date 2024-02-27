package com.task.jsonvalidator.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class JsonValidator {

    public Set<ValidationMessage> validate(JsonNode jsonObject, JsonSchema jsonSchema) {
        return jsonSchema.validate(jsonObject);
    }
}