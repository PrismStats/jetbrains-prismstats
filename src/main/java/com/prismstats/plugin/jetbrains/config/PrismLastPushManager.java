package com.prismstats.plugin.jetbrains.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class PrismLastPushManager {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String CONFIG_FILE_PATH = System.getProperty("user.home").replaceAll("\\\\", "/") + "/.prismstats/last_push.json";

    public static PrismLastPush loadConfig() {
        try {
            File configFile = new File(CONFIG_FILE_PATH);
            if (configFile.exists()) {
                return objectMapper.readValue(configFile, PrismLastPush.class);
            } else {
                return new PrismLastPush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new PrismLastPush();
        }
    }

    public static void saveConfig(PrismLastPush config) {
        try {
            File configFile = new File(CONFIG_FILE_PATH);
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }
            objectMapper.writeValue(configFile, config);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
