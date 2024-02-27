package com.task.jsonvalidator.controller;

import com.task.jsonvalidator.entity.JsonValidatorRequestBody;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.handler.SaveHandler;
import com.task.jsonvalidator.handler.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for the validation operation.
 */
@RestController
public class JsonValidatorController {

    @Autowired
    private ValidationHandler validationHandler;

    @Autowired
    private SaveHandler saveHandler;

    /**
     * Validates a given JSON object according to a given schema. The schema is also stored with a user-given name.
     *
     * @param schemaName the name chosen by the user in order to store the schema
     * @param jsonValidatorRequestBody an object that encapsulates the JSON object to be validated, and the path to the
     * schema chosen by the user for validation
     * @return a {@code Response} object containing information regarding the validity of the JSON object
     */
    @PostMapping("/validate")
    public Response validateJSON(@RequestParam String schemaName,
            @RequestBody JsonValidatorRequestBody jsonValidatorRequestBody) {
        saveHandler.saveSchema(jsonValidatorRequestBody.getSchemaSource(), schemaName);
        return validationHandler.validateJson(jsonValidatorRequestBody);
    }
}