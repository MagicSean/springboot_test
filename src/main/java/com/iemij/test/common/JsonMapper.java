package com.iemij.test.common;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Date;

public class JsonMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        OBJECT_MAPPER.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Date.class, new DateDeserializer());
        module.addSerializer(Date.class, new DateSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    public static ObjectMapper getObjectMapperInstance() {
        return OBJECT_MAPPER;
    }

    /**
     * 将对象转换成json格式的字符串
     *
     * @param obj 要转换的对象
     * @return json格式的字符串
     */
    public static String writeObjctToString(Object obj) {
        try {
            return getObjectMapperInstance().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("序列化对象异常", ex);
        }
    }

    public static <T> T parseObject(String json, TypeReference<T> typeReference) throws RuntimeException {
        if (StrUtil.isEmpty(json)) {
            throw new RuntimeException("parse object failed for empty input");
        }
        try {
            return getObjectMapperInstance().readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("parse object failed", e);
        }
    }

    /**
     * return a LinkedMap
     */
    public static <T> T parseObject(String json) throws RuntimeException {
        if (StrUtil.isEmpty(json)) {
            throw new RuntimeException("parse object failed for empty input");
        }
        TypeReference<T> typeReference = new TypeReference<T>() {
        };
        try {
            return getObjectMapperInstance().readValue(json, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("parse object failed", e);
        }
    }


    public static <T> T parseObjectWithNullValue(String json, TypeReference<T> typeReference) {
        if (StrUtil.isEmpty(json)) {
            return null;
        }
        try {
            return getObjectMapperInstance().readValue(json, typeReference);
        } catch (IOException e) {
            return null;
        }
    }
}
