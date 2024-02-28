package com.task.jsonvalidator.handler;

import com.networknt.schema.JsonSchema;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Handler for the schema saving operation.
 */
public interface SaveHandler {

    /**
     * Retrieves a JSON schema from the given source and stores it in the root path of the application, with the given name.
     *
     * @param schemaFile the schema to be stored
     * @param name the name chosen by the user for storing the schema
     */
    public JsonSchema saveSchema(final MultipartFile schemaFile, final String name) throws IOException;
}