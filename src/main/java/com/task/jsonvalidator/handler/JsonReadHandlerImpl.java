package com.task.jsonvalidator.handler;

import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaException;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.task.jsonvalidator.util.Constants;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class JsonReadHandlerImpl implements JsonReadHandler {

    public JsonSchema readSchema(final String name) throws JsonSchemaException {
        Path destinationFile = Paths.get(System.getProperty(Constants.CURRENT_DIRECTORY))
                .resolve(Paths.get(name + Constants.JSON_EXTENSION)).normalize().toAbsolutePath();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        return schemaFactory.getSchema(new File(destinationFile.toUri()).toURI());
    }
}