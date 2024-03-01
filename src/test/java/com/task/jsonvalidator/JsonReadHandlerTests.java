package com.task.jsonvalidator;

import com.networknt.schema.JsonSchemaException;
import com.task.jsonvalidator.handler.JsonReadHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class JsonReadHandlerTests {

    @Autowired
    private JsonReadHandler jsonReadHandler;

    private static Path validSchemaName = Paths.get("src").resolve(Paths.get("test")).resolve(Paths.get("resources"))
                    .resolve("testschema");
    private static Path invalidSchemaName = Paths.get("src").resolve(Paths.get("test")).resolve(Paths.get("resources"))
            .resolve("invalidschema");

    @Test
    void readValidSchema() {
        jsonReadHandler.readSchema(validSchemaName.toString());
    }

    @Test
    void readInvalidSchema() {
        assertThrows(JsonSchemaException.class, () -> jsonReadHandler.readSchema(invalidSchemaName.toString()));
    }
}