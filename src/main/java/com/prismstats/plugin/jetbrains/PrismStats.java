package com.prismstats.plugin.jetbrains;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.application.ApplicationManager;

import java.math.BigDecimal;

public class PrismStats {
    public static String TODAY_CODE_TIME = "4 Minutes";

    public PrismStats() {}

    public static void openDashboard() {
        BrowserUtil.browse("https://stats.prismstats.com");
    }

    public static BigDecimal getCurrentTimestamp() {
        return new BigDecimal(String.valueOf(System.currentTimeMillis() / 1000)).setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public static void updateStatusBarText() {
        BigDecimal now = getCurrentTimestamp();

        ApplicationManager.getApplication().executeOnPooledThread(new Runnable() {
            public void run() {
                try {
                    TODAY_CODE_TIME = "7 Minutes";
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
    }

    public static String getStatusBarText() {
        return TODAY_CODE_TIME;
    }
}
