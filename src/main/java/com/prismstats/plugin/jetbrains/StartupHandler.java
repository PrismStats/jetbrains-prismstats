package com.prismstats.plugin.jetbrains;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.prismstats.plugin.jetbrains.config.PrismConfig;
import com.prismstats.plugin.jetbrains.config.PrismConfigManager;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartupHandler implements StartupActivity.Background {

    @Override
    public void runActivity(@NotNull Project project) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if(PrismStats.hasInternetConnection()) {
                System.out.println(PrismStats.isValidKey());

                PrismConfig config = PrismConfigManager.loadConfig();
                System.out.println(config.getTime());

                // HANDLE STATUS BAR WIDGET UPDATE
                PrismStats.updateStatusBarText();
                StatusBar statusbar = WindowManager.getInstance().getStatusBar(project);
                statusbar.updateWidget("PrismStats");
            }
        }, 0, 60, TimeUnit.SECONDS);
    }
}
