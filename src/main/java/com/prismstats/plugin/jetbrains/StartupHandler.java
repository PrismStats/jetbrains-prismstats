package com.prismstats.plugin.jetbrains;

import com.google.gson.JsonObject;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.prismstats.plugin.jetbrains.collectors.GeneralCollector;
import com.prismstats.plugin.jetbrains.collectors.DataCollector;
import com.prismstats.plugin.jetbrains.collectors.FileCollector;
import com.prismstats.plugin.jetbrains.collectors.ProjectCollector;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class StartupHandler implements StartupActivity.Background {
    private static final String CLI_DOWNLOAD_LINK = "https://github.com/PrismStats/cli-download/releases/download/BETA/prismstats.exe";

    @Override
    public void runActivity(@NotNull Project project) {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

        if(!PrismStats.isCliInstalled()) {
            System.out.println("CLI not found...");

            Path path = Paths.get(System.getProperty("user.home").replaceAll("\\\\", "/") + "/.prismstats/cli");
            Path cliPath = Paths.get(System.getProperty("user.home").replaceAll("\\\\", "/") + "/.prismstats/cli/prismstats.exe");

            if(!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                } catch (Exception e) {
                    System.out.println("Error while create cli!");
                }
            }

            try (InputStream inputStream = new URL(CLI_DOWNLOAD_LINK).openStream()) {
                Files.copy(inputStream, cliPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("CLI was downloaded successfully!");
            } catch (Exception e) {
                System.out.println("Error while downloading cli!");
            }
        }

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if(PrismStats.hasInternetConnection()) {

                // HANDLE STATUS BAR WIDGET UPDATE
                PrismStats.updateStatusBarText();
                StatusBar statusbar = WindowManager.getInstance().getStatusBar(project);
                statusbar.updateWidget("PrismStats");

                if(FileCollector.getData().isEmpty()) return;

                JsonObject mainObject = new JsonObject();
                mainObject.addProperty("time", PrismStats.getCurrentTimestamp().toString());
                mainObject.add("files", FileCollector.getData());
                mainObject.add("data", DataCollector.getData());
                mainObject.add("project", ProjectCollector.getData());
                mainObject.add("general", GeneralCollector.getData());

                PrismStats.pushCLI(mainObject, PrismStats.getApiKey());

                FileCollector.clearData();
                DataCollector.clearData();
                ProjectCollector.clearData();
                GeneralCollector.clearData();
                mainObject = new JsonObject();
            }
        }, 0, 60, TimeUnit.SECONDS);
    }
}
