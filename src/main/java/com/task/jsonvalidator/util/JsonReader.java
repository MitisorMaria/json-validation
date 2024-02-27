package com.task.jsonvalidator.util;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import java.io.File;

/**
 * Utility class used for reading JSON objects from files.
 */
public class JsonReader {

    /**
     * Reads the file stored at a given path and creates a JSON schema from it.
     *
     * @param path the path of the schema
     * @return a {@code JsonSchema} object containing the schema from the file
     */
    public JsonSchema readSchema(String path) {
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        return schemaFactory.getSchema(new File(path).toURI());
    }
}