package com.task.jsonvalidator.handler;

import com.networknt.schema.JsonSchema;

/**
 * Handler used for reading JSON objects from files.
 */
public interface JsonReadHandler {

    /**
     * Reads the file stored at a given path and creates a JSON schema from it.
     *
     * @param path the path of the schema
     * @return a {@code JsonSchema} object containing the schema from the file
     */
    JsonSchema readSchema(String path);
}