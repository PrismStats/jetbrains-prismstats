package com.prismstats.plugin.jetbrains.collectors;

import com.google.gson.JsonObject;

public class DataCollector {
    public static JsonObject jsonObject = new JsonObject();

    public static int lines = 0;
    public static int chars = 0;

    public static void addLine(int count) { lines += count; };
    public static void addChar(int count) { chars += count; };

    public static void removeLine(int count) { lines -= count; };
    public static void removeChar(int count) { chars -= count; };

    public static JsonObject getData() {
        jsonObject.addProperty("lines", lines);
        jsonObject.addProperty("chars", chars);
        return jsonObject;
    }

    public static void clearData() {
        lines = 0;
        chars = 0;
        jsonObject = new JsonObject();
    }
}
