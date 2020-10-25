package com.karros.gpx.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHelper {

    public static String getJsonNodeValue(JsonNode node, String fieldName, String defaultValue) {
        JsonNode findNode = node.findValue(fieldName);
        if (findNode == null) {
            return defaultValue;
        }
        return findNode.asText(defaultValue);
    }

    public static String toJson(Object source) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(source);
        } catch (JsonGenerationException e) {
            throw new IllegalStateException(e);
        } catch (JsonMappingException e) {
            throw new IllegalStateException(e);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Boolean checkExistAndEmptyArray(JsonNode node, String fieldName) {
        return node.has(fieldName) && node.findValue(fieldName).toString().replace(" ", "").equals("[]");
    }

    public static String getText(JsonNode node, String fieldName) {
        JsonNode jsonNode = node.get(fieldName);
        return jsonNode == null ? "" : jsonNode.asText();
    }
}
