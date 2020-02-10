package io.github.bcalmac.overtime.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.Data;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class LocalDateSerializationTest {


    @Data
    static class TestBean {
        // Accept default ISO-8601 format
        LocalDate birthDate;
        // Use custom format
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
        LocalDate birthDateWithCustomFormat;
    }

    @Test
    void serializeDeserializeTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        // Register module that knows how to serialize java.time objects
        objectMapper.registerModule(new JavaTimeModule());
        // Ask Jackson to serialize dates as String (ISO-8601 by default)
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // The JSON string after serialization
        String json = "{\"birthDate\":\"2000-01-02\",\"birthDateWithCustomFormat\":\"03/02/2001\"}";
        // The object after deserialization
        TestBean object = new TestBean();
        object.setBirthDate(LocalDate.of(2000, 1, 2));
        object.setBirthDateWithCustomFormat(LocalDate.of(2001, 2, 3));

        // Assert serialization
        assertEquals(json, objectMapper.writeValueAsString(object));

        // Assert deserialization
        assertEquals(object, objectMapper.readValue(json, TestBean.class));
    }
}
