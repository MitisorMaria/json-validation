package com.task.jsonvalidator.controller;

import com.task.jsonvalidator.entity.JsonValidatorRequestBody;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.handler.SaveHandler;
import com.task.jsonvalidator.handler.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for the validation operation.
 */
@RestController
@RequestMapping("/validator")
public class JsonValidatorController {

    @Autowired
    private ValidationHandler validationHandler;

    @Autowired
    private SaveHandler saveHandler;

    @PostMapping("/validate")
    public Response validateJSON(@RequestParam String schemaName,
            @RequestBody JsonValidatorRequestBody jsonValidatorRequestBody) {
        saveHandler.saveSchema(jsonValidatorRequestBody.getSchemaSource(), schemaName);
        return validationHandler.validateJson(jsonValidatorRequestBody);
    }
}