package com.prismstats.plugin.jetbrains.collectors;

import com.google.gson.JsonObject;
import com.intellij.openapi.application.ApplicationInfo;
import com.prismstats.plugin.jetbrains.PrismStats;

public class GeneralCollector {
    public static JsonObject jsonObject = new JsonObject();

    public static JsonObject getData() {

        ApplicationInfo info = ApplicationInfo.getInstance();
        JsonObject ideObject = new JsonObject();
        ideObject.addProperty("name", info.getVersionName());
        ideObject.addProperty("version", info.getFullVersion());

        JsonObject systemObject = new JsonObject();
        systemObject.addProperty("user", System.getProperty("user.name"));
        systemObject.addProperty("name", System.getProperty("os.name"));
        systemObject.addProperty("version", System.getProperty("os.version"));

        JsonObject machineObject = new JsonObject();
        machineObject.addProperty("name", PrismStats.getSystemName());

        jsonObject.add("ide", ideObject);
        jsonObject.add("system", systemObject);
        jsonObject.add("machine", machineObject);

        return jsonObject;
    }

    public static void clearData() {
        jsonObject = new JsonObject();
    }
}
