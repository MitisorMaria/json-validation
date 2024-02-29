package com.task.jsonvalidator.controller;

import com.networknt.schema.JsonSchema;
import com.task.jsonvalidator.controller.paramvalidator.FilePathConstraint;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.handler.JsonReadHandler;
import com.task.jsonvalidator.handler.SaveHandler;
import com.task.jsonvalidator.handler.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Controller for the validation operation.
 */
@RestController
@Validated
public class JsonValidatorController {

    private final ValidationHandler validationHandler;

    private final SaveHandler saveHandler;

    private final JsonReadHandler jsonReadHandler;

    @Autowired
    public JsonValidatorController(ValidationHandler validationHandler, SaveHandler saveHandler, JsonReadHandler jsonReadHandler) {
        this.validationHandler = validationHandler;
        this.saveHandler = saveHandler;
        this.jsonReadHandler = jsonReadHandler;
    }

    /**
     * Validates a given JSON object according to a given schema. The schema is also stored with a user-given name.
     *
     * @param schemaName the name chosen by the user in order to store the schema
     * @param jsonObject the JSON object to be validated
     * @param schemaFile the schema file used for validation
     * @return a {@code Response} object containing information regarding the validity of the JSON object
     */
    @PostMapping(value = "/validate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Response validateJSON(@RequestParam @FilePathConstraint final String schemaName,
            @RequestPart final MultipartFile schemaFile,
            @RequestPart final String jsonObject) throws IOException {
        saveHandler.saveSchema(schemaFile, schemaName);
        JsonSchema uploadedSchema = jsonReadHandler.readSchema(schemaName);
        return validationHandler.validateJson(uploadedSchema, jsonObject);
    }
}