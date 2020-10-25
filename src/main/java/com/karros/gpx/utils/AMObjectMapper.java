package com.karros.gpx.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.TextNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AMObjectMapper extends ObjectMapper {

    private static final Logger logger = LoggerFactory.getLogger(AMObjectMapper.class);

    public AMObjectMapper(boolean useSnakeCase) {
        if (useSnakeCase) {
            this.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        }
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        //this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static JsonNode toJsonNode(Object object, boolean useSnakeCase) {
        JsonNode result = null;
        try {
            if (object instanceof JsonNode) {
                result = (JsonNode) object;
            } else {
                ObjectMapper mapper = new AMObjectMapper(useSnakeCase);
                if (object instanceof String) {
                    result = mapper.readTree((String) object);
                } else {
                    result = mapper.valueToTree(object);
                }
            }
        } catch (Exception e) {
            if (object instanceof String) {
                result = new TextNode((String) object);
            } else {
                logger.error("Cannot convert " + object.toString() + " to json", e);
            }
        }
        return result;
    }

    public static <T> T toObject(JsonNode node, Class<T> clazz, boolean useSnakeCase) {
        return new AMObjectMapper(useSnakeCase).convertValue(node, clazz);
    }

    public static <T> T toObject(String jsonString, Class<T> clazz, boolean useSnakeCase) throws IOException {
        return new AMObjectMapper(useSnakeCase).readValue(jsonString, clazz);
    }

    public static String toJsonString(Object object, boolean useSnakeCase) throws JsonProcessingException {
        return new AMObjectMapper(useSnakeCase).writeValueAsString(object);
    }

    public static <T> T toObject(String jsonString, Class<T> clazz) {
        T result = null;
        ObjectMapper mapper = new ObjectMapper();
        try {
            result = mapper.readValue(jsonString, clazz);
        } catch (IOException e) {
            logger.error("Can not convert json to {} object, error {}", clazz.getSimpleName(), e.getMessage());
        }
        return result;
    }

    public static <T> Predicate<T> distinctByKeys(Function<? super T, ?>... keyList) {
        final Map<List<?>, Boolean> seen = new ConcurrentHashMap<>();
        return t -> {
            final List<?> keys = Arrays.stream(keyList).map(k -> k.apply(t)).collect(Collectors.toList());
            return seen.putIfAbsent(keys, Boolean.TRUE) == null;
        };
    }
}
