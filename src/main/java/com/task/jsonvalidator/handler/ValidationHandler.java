package com.task.jsonvalidator.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import com.task.jsonvalidator.entity.JsonValidatorRequestBody;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.util.Constants;
import com.task.jsonvalidator.util.JsonReader;

import com.task.jsonvalidator.validator.JsonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class ValidationHandler {

    @Autowired
    private JsonValidator jsonValidator;

    public Response validateJson(JsonValidatorRequestBody jsonValidatorRequestBody) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;

        JsonSchema schema = new JsonReader().readSchema(jsonValidatorRequestBody.getSchemaSource());
        try {
            String jsonStr = mapper.writeValueAsString(jsonValidatorRequestBody.getJsonObject());
            jsonNode = mapper.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Set<ValidationMessage> validationMessages = jsonValidator.validate(jsonNode, schema);
        return validationMessages.isEmpty() ? new Response(Constants.VALID_RESPONSE, true)
                : new Response(Constants.INVALID_RESPONSE, false);
    }
}