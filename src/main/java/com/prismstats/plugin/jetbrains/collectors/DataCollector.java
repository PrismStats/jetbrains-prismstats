package com.prismstats.plugin.jetbrains.collectors;
import com.google.gson.JsonObject;

import java.util.concurrent.atomic.AtomicInteger;

public class DataCollector {
    private static volatile JsonObject jsonObject = new JsonObject();
    private static final AtomicInteger lines = new AtomicInteger(0);
    private static final AtomicInteger chars = new AtomicInteger(0);

    public static void addLine(int count) { lines.addAndGet(count); }
    public static void addChar(int count) { chars.addAndGet(count); }
    public static void removeLine(int count) { lines.addAndGet(-count); }
    public static void removeChar(int count) { chars.addAndGet(-count); }

    public static synchronized JsonObject getData() {
        JsonObject copy = jsonObject.deepCopy();
        copy.addProperty("lines", lines.get());
        copy.addProperty("chars", chars.get());
        return copy;
    }

    public static synchronized void clearData() {
        lines.set(0);
        chars.set(0);
        jsonObject = new JsonObject();
    }
}