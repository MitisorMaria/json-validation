package com.task.jsonvalidator;

import com.fasterxml.jackson.core.JsonParseException;
import com.networknt.schema.JsonSchemaException;
import com.task.jsonvalidator.controller.JsonValidatorController;
import com.task.jsonvalidator.entity.Response;
import com.task.jsonvalidator.handler.JsonReadHandler;
import com.task.jsonvalidator.handler.SaveHandler;
import com.task.jsonvalidator.handler.ValidationHandler;
import com.task.jsonvalidator.util.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@AutoConfigureJsonTesters
@WebMvcTest(JsonValidatorController.class)
public class JsonValidatorControllerTests {

    @Autowired private MockMvc mvc;
    @MockBean private ValidationHandler validationHandler;
    @MockBean private SaveHandler saveHandler;
    @MockBean private JsonReadHandler jsonReadHandler;
    @Autowired private JacksonTester<Response> jsonResponse;
    private static MockMultipartFile mockMultipartFile;
    private static MockMultipartFile mockJsonFile;

    @BeforeAll
    static void setup() {
        String contentType = String.valueOf(MediaType.APPLICATION_JSON);
        mockMultipartFile =
                new MockMultipartFile("schemaFile", "", contentType, "{\"json\": \"someValue\"}".getBytes());
        mockJsonFile = new MockMultipartFile("jsonObject", "", contentType, "{\"json\": \"someValue\"}".getBytes());
    }

    @Test
    public void validJsonAccordingToSchema() throws Exception {
        when(validationHandler.validateJson(any(), any())).thenReturn(new Response(Constants.VALID_RESPONSE, true));

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=test").file(mockMultipartFile).file(mockJsonFile)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResponse.write(new Response(Constants.VALID_RESPONSE, true)).getJson());
    }

    @Test
    public void invalidJsonAccordingToSchema() throws Exception {
        when(validationHandler.validateJson(any(), any())).thenReturn(new Response(Constants.INVALID_RESPONSE, false));

        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=test").file(mockMultipartFile).file(mockJsonFile)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResponse.write(new Response(Constants.INVALID_RESPONSE, false)).getJson());
    }

    @Test
    public void badParameter() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=test*").file(mockMultipartFile)
                        .file(mockJsonFile).accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResponse.write(new Response(Constants.WRONG_FILE_NAME, false)).getJson());
    }

    @Test
    public void emptyParameter() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=").file(mockMultipartFile).file(mockJsonFile)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo(
                jsonResponse.write(new Response(Constants.WRONG_FILE_NAME, false)).getJson());
    }

    @Test
    public void badSchema() throws Exception {
        doThrow(JsonSchemaException.class).when(jsonReadHandler).readSchema(any());
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=test").file(mockMultipartFile).file(mockJsonFile)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains(Constants.ERROR_INVALID_JSON_SCHEMA);
    }

    @Test
    public void emptySchema() throws Exception {
        doThrow(IOException.class).when(saveHandler).saveSchema(any(), any());
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=test").file(mockMultipartFile).file(mockJsonFile)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void badJson() throws Exception {
        doThrow(JsonParseException.class).when(validationHandler).validateJson(any(), any());
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=test").file(mockMultipartFile).file(mockJsonFile)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains(Constants.ERROR_INVALID_JSON);
    }

    @Test
    public void noJson() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                MockMvcRequestBuilders.multipart("/validate?schemaName=test").file(mockMultipartFile)
                        .accept(MediaType.APPLICATION_JSON)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Required request part 'jsonObject' is not present");
    }

    @Test
    public void noFile() throws Exception {
        MockHttpServletResponse response =
                mvc.perform(MockMvcRequestBuilders.multipart("/validate?schemaName=test").file(mockJsonFile))
                        .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).contains("Required request part 'schemaFile' is not present");
    }
}