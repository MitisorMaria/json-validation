package com.task.jsonvalidator.handler;

import com.task.jsonvalidator.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class SaveHandlerImpl implements SaveHandler {

    private final Environment env;
    private final Path rootLocation;

    @Autowired
    public SaveHandlerImpl(final Environment env) {
        this.rootLocation = Paths.get(System.getProperty(Constants.CURRENT_DIRECTORY));
        this.env = env;
    }

    @Override
    public void saveSchema(final MultipartFile schemaFile, final String name) throws IOException {
        if (schemaFile.isEmpty()) {
            throw new IOException(Constants.ERROR_RESPONSE_EMPTY_FILE);
        }
        Path destinationFile =
                this.rootLocation.resolve(Paths.get(name + Constants.JSON_EXTENSION)).normalize().toAbsolutePath();
        try (InputStream inputStream = schemaFile.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}