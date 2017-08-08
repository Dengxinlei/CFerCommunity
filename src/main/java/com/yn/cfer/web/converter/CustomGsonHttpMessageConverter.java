package com.yn.cfer.web.converter;

import com.google.gson.GsonBuilder;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.text.DateFormat;

/**
 * Created by admin on 16/11/4.
 */
public class CustomGsonHttpMessageConverter extends GsonHttpMessageConverter {

    public CustomGsonHttpMessageConverter() {
        super();
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(java.util.Date.class, new GsonDateSerializer()).setDateFormat(DateFormat.LONG);
        builder.registerTypeAdapter(java.util.Date.class, new GsonDateDeserializer()).setDateFormat(DateFormat.LONG);
        builder.serializeNulls();
//        builder.excludeFieldsWithoutExposeAnnotation();
        this.setGson(builder.create());
    }

}
