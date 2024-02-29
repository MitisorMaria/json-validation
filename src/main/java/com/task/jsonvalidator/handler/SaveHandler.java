package com.task.jsonvalidator.handler;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Handler for the schema saving operation.
 */
public interface SaveHandler {

    /**
     * Stores a JSON schema in the root path of the application, with the given name.
     *
     * @param schemaFile the file containing the schema to be stored
     * @param name the name chosen by the user for storing the schema
     */
    void saveSchema(final MultipartFile schemaFile, final String name) throws IOException;
}