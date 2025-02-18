package com.cleanroommc.modularui.utils;

import com.google.common.base.CaseFormat;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.lang.reflect.Type;
import java.util.Map;

public class Alignment {

    private static final Map<String, Alignment> ALIGNMENT_MAP = new Object2ObjectOpenHashMap<>();

    public final float x, y;

    public static final Alignment TopLeft = new Alignment(0, 0, "TopLeft");
    public static final Alignment TopCenter = new Alignment(0.5f, 0, "TopCenter");
    public static final Alignment TopRight = new Alignment(1, 0, "TopRight");
    public static final Alignment CenterLeft = new Alignment(0, 0.5f, "CenterLeft");
    public static final Alignment Center = new Alignment(0.5f, 0.5f, "Center");
    public static final Alignment CenterRight = new Alignment(1, 0.5f, "CenterRight");
    public static final Alignment BottomLeft = new Alignment(0, 1, "BottomLeft");
    public static final Alignment BottomCenter = new Alignment(0.5f, 1, "BottomCenter");
    public static final Alignment BottomRight = new Alignment(1, 1, "BottomRight");

    public static final Alignment[] ALL = {
            TopLeft, TopCenter, TopRight,
            CenterLeft, Center, CenterRight,
            BottomLeft, BottomCenter, BottomRight
    };

    public static final Alignment[] CORNERS = {
            TopLeft, TopRight,
            BottomLeft, BottomRight
    };

    public Alignment(float x, float y) {
        this(x, y, null);
    }

    private Alignment(float x, float y, String name) {
        this.x = x;
        this.y = y;
        if (name != null) {
            ALIGNMENT_MAP.put(name, this);
            ALIGNMENT_MAP.put(CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name), this);
            String abbrev = name.replaceAll("[a-z]", "");
            ALIGNMENT_MAP.put(abbrev, this);
            ALIGNMENT_MAP.put(abbrev.toLowerCase(), this);
        }
    }

    public static class Json implements JsonDeserializer<Alignment> {

        @Override
        public Alignment deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            if (!json.isJsonObject()) {
                Alignment alignment = ALIGNMENT_MAP.get(json.getAsString());
                if (alignment == null) {
                    throw new JsonParseException("Can't find alignment for " + json.getAsString());
                }
                return alignment;
            }
            float x = JsonHelper.getFloat(json.getAsJsonObject(), 0f, "x");
            float y = JsonHelper.getFloat(json.getAsJsonObject(), 0f, "y");
            return new Alignment(x, y);
        }
    }
}
