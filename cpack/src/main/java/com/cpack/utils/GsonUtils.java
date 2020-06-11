package com.cpack.utils;

import com.google.gson.*;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;

/**
 * json工具类
 */
public class GsonUtils {
    private static Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .serializeNulls()
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
        @Override
        public JsonElement serialize(Double src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == src.longValue())
                return new JsonPrimitive(src.longValue());
            return new JsonPrimitive(src);
        }
    }).create();

    public static <T> T fromJson(String json, Class clazz) {
        if (StringUtils.isEmpty(json))
            return null;
        return (T) gson.fromJson(json, clazz);
    }

    public static String toJson(Object src) {
        if (src == null)
            return null;
        return gson.toJson(src);
    }
}
