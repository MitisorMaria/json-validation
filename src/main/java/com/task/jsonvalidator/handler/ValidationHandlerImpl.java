package com.task.jsonvalidator.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.ValidationMessage;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.util.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class ValidationHandlerImpl implements ValidationHandler {

    private final JsonValidator jsonValidator;

    @Autowired
    public ValidationHandlerImpl(final JsonValidator jsonValidator) {
        this.jsonValidator = jsonValidator;
    }

    @Override
    public Response validateJson(final JsonSchema schema, final String jsonObject) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonObject);

        Set<ValidationMessage> validationMessages = jsonValidator.validate(jsonNode, schema);
        return validationMessages.isEmpty() ? new Response(Constants.VALID_RESPONSE, true)
                : new Response(Constants.INVALID_RESPONSE, false);
    }
}