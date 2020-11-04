package com.lsd.gateway.util;

import com.google.gson.*;
import org.springframework.boot.json.JsonParseException;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: nhsoft.lsd
 * @Description:
 * @Date:Create：in 2020-11-03 22:56
 * @Modified By：
 */
public class GsonUtils {

    public static Gson toBuilderGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.disableHtmlEscaping();
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        gsonBuilder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return df.parse(json.getAsString());
                } catch (ParseException e) {
                    return null;
                }
            }
        });
        gsonBuilder.registerTypeAdapter(Integer.class, new JsonDeserializer<Integer>() {

            @Override
            public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return Integer.parseInt(json.getAsString());
                } catch (Exception e) {
                    return null;
                }
            }

        });
        gsonBuilder.registerTypeAdapter(BigDecimal.class, new JsonDeserializer<BigDecimal>() {

            @Override
            public BigDecimal deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                try {
                    return new BigDecimal(json.getAsString());
                } catch (Exception e) {
                    return null;
                }
            }

        });
        return gsonBuilder.create();
    }
}
