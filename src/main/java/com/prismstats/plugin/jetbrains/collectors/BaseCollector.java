package com.prismstats.plugin.jetbrains.collectors;

import com.intellij.openapi.application.ApplicationInfo;
import com.prismstats.plugin.jetbrains.PrismStats;
import net.minidev.json.JSONObject;

public class BaseCollector {
    public static JSONObject jsonObject = new JSONObject();

    public static JSONObject getData() {
        ApplicationInfo info = ApplicationInfo.getInstance();

        jsonObject.put("ide", info.getVersionName());
        jsonObject.put("version", info.getFullVersion());
        jsonObject.put("system", System.getProperty("os.name"));
        jsonObject.put("user", System.getProperty("user.name"));
        jsonObject.put("time", PrismStats.getCurrentTimestamp());

        jsonObject.put("projects", ProjectCollector.getData());

        return jsonObject;
    }
}
