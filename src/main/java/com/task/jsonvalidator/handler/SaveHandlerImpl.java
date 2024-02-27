package com.task.jsonvalidator.handler;

import com.networknt.schema.JsonSchema;
import com.task.jsonvalidator.util.Constants;
import com.task.jsonvalidator.util.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


@Component
public class SaveHandlerImpl implements SaveHandler {

    private final Environment env;

    @Autowired
    public SaveHandlerImpl(Environment env) {
        this.env = env;
    }

    @Override
    public void saveSchema(final String schemaSource, final String name) {
        JsonSchema schema = new JsonReader().readSchema(schemaSource);

        String savePath = env.getProperty(Constants.SAVE_PATH) + Constants.SLASH + name + Constants.JSON_EXTENSION;
        File file = new File(savePath);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(schema.toString().substring(Constants.SCHEMA_START_INDEX));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}