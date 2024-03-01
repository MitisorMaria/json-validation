package com.task.jsonvalidator;

import com.task.jsonvalidator.handler.SaveHandler;
import com.task.jsonvalidator.util.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
public class SaveHandlerTests {
    @Autowired private SaveHandler saveHandler;

    private static MultipartFile mockMultipartFileValid;
    private static MultipartFile mockMultipartFileEmpty;
    private static String name = "saveHandlerTest";
    private static Path originalSchemaPath;
    private static Path emptySchemaPath;
    private static Path sideEffectSchemaPath;

    @BeforeAll
    static void setup() {
        Path commonPath =
                Paths.get(".").resolve(Paths.get("src")).resolve(Paths.get("test")).resolve(Paths.get("resources"));
        originalSchemaPath =
                commonPath.resolve(Paths.get("testschema" + Constants.JSON_EXTENSION)).normalize().toAbsolutePath();
        emptySchemaPath =
                commonPath.resolve(Paths.get("emptyschema" + Constants.JSON_EXTENSION)).normalize().toAbsolutePath();
        sideEffectSchemaPath =
                Paths.get(".").resolve(Paths.get(name + Constants.JSON_EXTENSION)).normalize().toAbsolutePath();
        String originalFileName = "testschema.json";
        String contentType = "application/json";
        byte[] contentValid;
        byte[] contentEmpty;
        try {
            contentValid = Files.readAllBytes(originalSchemaPath);
            contentEmpty = Files.readAllBytes(emptySchemaPath);
        } catch (final IOException e) {
            throw new RuntimeException();
        }
        mockMultipartFileValid = new MockMultipartFile(name, originalFileName, contentType, contentValid);
        mockMultipartFileEmpty = new MockMultipartFile(name, originalFileName, contentType, contentEmpty);
    }

    @Test
    void saveValidSchema() {
        try {
            saveHandler.saveSchema(mockMultipartFileValid, name);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Test
    void saveEmptySchema() {
        Exception exception = assertThrows(IOException.class, () -> {
            saveHandler.saveSchema(mockMultipartFileEmpty, name);
        });

        String expectedMessage = Constants.ERROR_RESPONSE_EMPTY_FILE;
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @AfterEach
    void tearDown() {
        try {
            Files.deleteIfExists(sideEffectSchemaPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}