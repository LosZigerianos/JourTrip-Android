package com.zigerianos.jourtrip.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public final class ISODateAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {

    private final List<DateFormat> dateFormats = new ArrayList<>();

    public ISODateAdapter() {
        DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
        iso8601Format.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateFormat iso8601SimpleFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        iso8601SimpleFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        dateFormats.add(iso8601Format);
        dateFormats.add(iso8601SimpleFormat);
        dateFormats.add(simpleDateFormat);
        dateFormats.add(timeFormat);
    }

    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        String dateFormatAsString = dateFormats.get(0).format(src);
        return new JsonPrimitive(dateFormatAsString);
    }

    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        Date date = deserializeToDate(json);
        if (typeOfT == Date.class) {
            return date;
        } else if (typeOfT == Timestamp.class) {
            return new Timestamp(date.getTime());
        } else if (typeOfT == java.sql.Date.class) {
            return new java.sql.Date(date.getTime());
        } else {
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + typeOfT);
        }
    }

    private Date deserializeToDate(JsonElement json) {
        for (DateFormat dateFormat : dateFormats) {
            try {
                return dateFormat.parse(json.getAsString());
            } catch (ParseException ignored) {
            }
        }

//        throw new JsonParseException("Unparseable date: \"" + json.getAsString() + "\". Supported formats: " + Arrays.toString(DATE_FORMATS));
        throw new JsonParseException("Unparseable date: '" + json.getAsString() + "'.");
    }
}