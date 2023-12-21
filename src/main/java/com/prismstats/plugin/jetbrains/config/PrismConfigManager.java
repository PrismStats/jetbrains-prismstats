package com.prismstats.plugin.jetbrains.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class PrismConfigManager {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final String CONFIG_FILE_PATH = System.getProperty("user.home").replaceAll("\\\\", "/") + "/.prismstats/data.json";

    public static PrismConfig loadConfig() {
        System.out.println(System.getProperty("user.home").replaceAll("\\\\", "/"));
        try {
            File configFile = new File(CONFIG_FILE_PATH);
            if (configFile.exists()) {
                return objectMapper.readValue(configFile, PrismConfig.class);
            } else {
                return new PrismConfig();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new PrismConfig();
        }
    }

    public static void saveConfig(PrismConfig config) {
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