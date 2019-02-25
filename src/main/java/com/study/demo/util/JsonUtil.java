package com.study.demo.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * json util
 */
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object obj){
        String value = "";
        try {
            value = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
    }
}
