package com.task.jsonvalidator.handler;

/**
 * Handler for the schema saving operation.
 */
public interface SaveHandler {

    /**
     * Retrieves a JSON schema from the given source and stores it at the location configured in the application.properties
     * file, with the given name.
     *
     * @param schemaSource the path of the schema to be stored
     * @param name the name chosen by the user for storing the schema
     */
    public void saveSchema(final String schemaSource, final String name);
}