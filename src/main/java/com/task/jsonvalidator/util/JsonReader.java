package com.task.jsonvalidator.util;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;

import java.io.File;


public class JsonReader {

    public JsonSchema readSchema(String path) {
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance( SpecVersion.VersionFlag.V201909 );
        return schemaFactory.getSchema(new File(path).toURI());
    }

}