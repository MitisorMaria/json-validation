package com.task.jsonvalidator.controller;

import com.task.jsonvalidator.entity.JsonValidatorRequestBody;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.handler.ValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Controller for the validation operation.
 *
 */
@RestController
@RequestMapping("/validator")
public class JsonValidatorController {

    @Autowired
    private ValidationHandler validationHandler;

    @PostMapping("/validate")
    public Response validateJSON(@RequestBody JsonValidatorRequestBody jsonValidatorRequestBody) {
        return validationHandler.validateJson(jsonValidatorRequestBody);
    }
}