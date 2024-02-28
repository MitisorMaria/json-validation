package com.task.jsonvalidator.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;


@Component
public class ValidationHandlerImpl implements ValidationHandler{

    private final JsonValidator jsonValidator;

    @Autowired
    public ValidationHandlerImpl(JsonValidator jsonValidator) {
        this.jsonValidator = jsonValidator;
    }

    @Override
    public Response validateJson(JsonSchema schema, String jsonObject) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode;

        try {
            jsonNode = mapper.readTree(jsonObject);
        } catch (JsonProcessingException e) {
            return new Response(Constants.PROCESSING_ERROR_RESPONSE, false);
        }

        Set<ValidationMessage> validationMessages = jsonValidator.validate(jsonNode, schema);
        return validationMessages.isEmpty() ? new Response(Constants.VALID_RESPONSE, true)
                : new Response(Constants.INVALID_RESPONSE, false);
    }
}