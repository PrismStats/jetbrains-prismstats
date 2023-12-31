package com.prismstats.plugin.jetbrains.collectors;

import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationInfo;
import com.prismstats.plugin.jetbrains.PrismStats;

public class BaseCollector {
    public static JsonObject jsonObject = new JsonObject();

    public static JsonObject getData() {
        ApplicationInfo info = ApplicationInfo.getInstance();
        jsonObject.addProperty("ideName", info.getVersionName());
        jsonObject.addProperty("ideVersion", info.getFullVersion());
        jsonObject.addProperty("system", System.getProperty("os.name"));
        jsonObject.addProperty("systemVersion", System.getProperty("os.version"));
        jsonObject.addProperty("systemName", PrismStats.getSystemName());
        jsonObject.addProperty("systemUser", System.getProperty("user.name"));
        jsonObject.addProperty("systemArch", System.getProperty("os.arch"));
        jsonObject.addProperty("time", PrismStats.getCurrentTimestamp());
        jsonObject.add("projects", ProjectCollector.getData());
        jsonObject.add("files", FileCollector.getData());
        jsonObject.add("data", DataCollector.getData());
        return jsonObject;
    }

    public static void clearData() {
        jsonObject = new JsonObject();
    }
}
