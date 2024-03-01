package com.task.jsonvalidator;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.handler.ValidationHandler;
import com.task.jsonvalidator.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class JsonValidatorTests {

    @Autowired private ValidationHandler validationHandler;
    private final static Response VALID = new Response(Constants.VALID_RESPONSE, true);
    private final static Response INVALID = new Response(Constants.INVALID_RESPONSE, false);
    private static JsonSchema schema;

    @BeforeAll
    static void setup() {
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V201909);
        schema = schemaFactory.getSchema("{\n" + "\t\"$schema\": \"https://json-schema.org/draft/2019-09/schema#\",\n"
                + "\t\"$id+\": \"http://my-paintings-api.com/schemas/painting-schema.json\",\n"
                + "\t\"type\": \"object\",\n" + "\t\"title\": \"Painting\",\n"
                + "\t\"description\": \"Painting information\",\n" + "\t\"additionalProperties\": true,\n"
                + "\t\"required\": [\"name\", \"artist\", \"description\", \"tags\"],\n" + "\t\"properties\": {\n"
                + "\t\t\"name\": {\n" + "\t\t\t\"type\": \"string\",\n" + "\t\t\t\"description\": \"Painting name\"\n"
                + "\t\t},\n" + "\t\t\"artist\": {\n" + "\t\t\t\"type\": \"string\",\n" + "\t\t\t\"maxLength\": 50,\n"
                + "\t\t\t\"description\": \"Name of the artist\"\n" + "\t\t},\n" + "\t\t\"description\": {\n"
                + "\t\t\t\"type\": [\"string\", \"null\"],\n" + "\t\t\t\"description\": \"Painting description\"\n"
                + "\t\t},\n" + "\t\t\"tags\": {\n" + "\t\t\t\"type\": \"array\",\n"
                + "\t\t\t\"items\": { \"$ref\": \"#/$defs/tag\" }\n" + "\t\t}\n" + "\t},\n" + "\t\"$defs\": {\n"
                + "\t\t\"tag\": {\n" + "\t\t\t\"type\": \"string\",\n"
                + "\t\t\t\"enum\": [\"oil\", \"watercolor\", \"digital\", \"famous\"]\n" + "\t\t}\n" + "\t}\n" + "}");
    }

    @Test
    void jsonIsValidAccordingToSchema() {
        String invalidAccordingToSchema =
                "{  \n" + "  \"name\": \"Emma Watson\",  \n" + "  \"artist\": \"Paul Walker\",  \n"
                        + "  \"description\": null,  \n" + "  \"tags\": [\"oil\", \"famous\"]  \n" + "} ";
        Response response;
        try {
            response = validationHandler.validateJson(schema, invalidAccordingToSchema);
        } catch (JsonProcessingException e) {
            response = new Response(Constants.ERROR_INVALID_JSON, false);
        }
        Assertions.assertEquals(VALID, response);
    }

    @Test
    void jsonIsInvalidAccordingToSchema() {
        String invalidAccordingToSchema =
                "{  \n" + "  \"name\": \"Emma Watson\",  \n" + "  \"artist\": \"Paul Walker\",  \n"
                        + "  \"description\": null,  \n" + "  \"tags\": [\"nana\", \"famous\"]  \n" + "} ";
        Response response;
        try {
            response = validationHandler.validateJson(schema, invalidAccordingToSchema);
        } catch (JsonProcessingException e) {
            response = new Response(Constants.ERROR_INVALID_JSON, false);
        }
        Assertions.assertEquals(INVALID, response);
    }

    @Test
    void jsonIsInvalid() {
        String invalidAccordingToSchema =
                "{  \n" + "  name\": \"Emma Watson\",  \n" + "  \"artist\": \"Paul Walker\",  \n"
                        + "  \"description\": null,  \n" + "  \"tags\": [\"nana\", \"famous\"]  \n" + "} ";
        assertThrows(JsonParseException.class, () -> validationHandler.validateJson(schema, invalidAccordingToSchema));
    }
}