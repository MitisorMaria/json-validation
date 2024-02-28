package com.task.jsonvalidator.handler;

import com.networknt.schema.JsonSchema;
import com.task.jsonvalidator.util.Constants;
import com.task.jsonvalidator.util.JsonReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Component
public class SaveHandlerImpl implements SaveHandler {

    private final Environment env;
    private final Path rootLocation;

    @Autowired
    public SaveHandlerImpl(final Environment env) {
        this.rootLocation = Paths.get(System.getProperty(Constants.CURRENT_DIRECTORY));
        this.env = env;
    }

    @Override
    public JsonSchema saveSchema(final MultipartFile schemaFile, final String name) throws IOException {
        JsonSchema toReturn;
        try {
            if (schemaFile.isEmpty()) {
                throw new IOException(Constants.ERROR_RESPONSE_EMPTY_FILE);
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(name + Constants.JSON_EXTENSION))
                    .normalize().toAbsolutePath();
            try (InputStream inputStream = schemaFile.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                toReturn = new JsonReader().readSchema(destinationFile.toString());
            }
        }
        catch (Exception e) {
            throw new IOException(Constants.ERROR_RESPONSE, e);
        }
        return toReturn;
    }
}