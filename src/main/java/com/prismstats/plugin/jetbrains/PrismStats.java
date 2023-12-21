package com.prismstats.plugin.jetbrains;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.application.ApplicationManager;
import com.prismstats.plugin.jetbrains.config.PrismConfig;
import com.prismstats.plugin.jetbrains.config.PrismConfigManager;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;

public class PrismStats {
    public PrismStats() {}

    public static void openDashboard() {
        BrowserUtil.browse("https://prismstats.com/dashboard");
    }
    public static boolean hasInternetConnection() {
        try {
            InetAddress inetAddress = InetAddress.getByName("8.8.8.8");
            return inetAddress.isReachable(1000);
        } catch (IOException e) {
            return false;
        }
    }
    public static boolean isValidKey() {
        PrismConfig config = PrismConfigManager.loadConfig();
        String configKey = config.getKey();

        return configKey.startsWith("ps_") && configKey.indexOf('.') != -1;
    }

    public static boolean isCliInstalled() {
        String cliFilePath = System.getProperty("user.home").replaceAll("\\\\", "/") + "/.prismstats/cli/prismstats.exe";
        File cliFile = new File(cliFilePath);
        return cliFile.exists();
    }

    public static BigDecimal getCurrentTimestamp() {
        return new BigDecimal(String.valueOf(System.currentTimeMillis() / 1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public static void updateStatusBarText() {
        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            public void run() {
                PrismConfig config = PrismConfigManager.loadConfig();
                config.setTime(config.getTime() + 1);
                PrismConfigManager.saveConfig(config);
            }
        });
    }
    public static String getStatusBarText() {
        PrismConfig config = PrismConfigManager.loadConfig();
        if(config.getTime() == null) return "<5";
        return config.getTime().toString();
    }
}
