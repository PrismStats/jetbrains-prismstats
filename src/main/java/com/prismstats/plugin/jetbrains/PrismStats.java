package com.prismstats.plugin.jetbrains;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.messages.MessageBusConnection;
import com.prismstats.plugin.jetbrains.config.PrismConfig;
import com.prismstats.plugin.jetbrains.config.PrismConfigManager;
import com.prismstats.plugin.jetbrains.config.PrismLastPush;
import com.prismstats.plugin.jetbrains.config.PrismLastPushManager;
import com.prismstats.plugin.jetbrains.listener.DocumentListener;
import net.minidev.json.JSONObject;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.InetAddress;

public class PrismStats implements ApplicationComponent {
    public PrismStats() {}

    public void initComponent() {
        registerListeners();
    }

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

    public static void registerListeners() {
        ApplicationManager.getApplication().invokeLater(new Runnable() {
            @Override
            public void run() {
                Disposable disposable = Disposer.newDisposable("PrismListener");
                MessageBusConnection messageBusConnection = ApplicationManager.getApplication().getMessageBus().connect();

                EditorFactory.getInstance().getEventMulticaster().addDocumentListener(new DocumentListener(), disposable);
            }
        });
    }

    @Nullable
    public static VirtualFile getFile(@Nullable Document document) {
        return isDocumentAvailable(document) ? getVirtualFileFromDocument(document) : null;
    }

    private static boolean isDocumentAvailable(@Nullable Document document) {
        if (document == null) return false;
        FileDocumentManager.getInstance();
        return true;
    }

    @Nullable
    private static VirtualFile getVirtualFileFromDocument(Document document) {
        return FileDocumentManager.getInstance().getFile(document);
    }

    public static Editor[] getEditors(Document document) {
        return EditorFactory.getInstance().getEditors(document);
    }

    @Nullable
    public static Project getProject(Document document) {
        Editor[] editors = getEditors(document);
        if(editors.length > 0) {
            return editors[0].getProject();
        }
        return null;
    }

    public static String getLanguage(final VirtualFile file) {
        FileType type = file.getFileType();
        return type.getName();
    }

    public static void pushCLI(JSONObject jsonObject) {
        System.out.println("Trying to push CLI data...");

        String directoryPath = System.getProperty("user.home") + "/.prismstats/cli".replaceAll("/", "\\\\");
        String command = ".\\prismstats push";

        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");

        PrismLastPush lastPush = PrismLastPushManager.loadConfig();
        lastPush.setData(jsonObject.toJSONString());
        PrismLastPushManager.saveConfig(lastPush);

        ProcessBuilder processBuilder = new ProcessBuilder();

        if(isWindows) {
            processBuilder.directory(new File(directoryPath));
            processBuilder.command("cmd.exe", "/c", "cd " + directoryPath + " && " + command);
        } else {
            System.out.println("Linux");
        }

       try {
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

           System.out.println("L: " + output);

            int exitVal = process.waitFor();

            System.out.println("E: " + exitVal);

            if (exitVal == 0) {
                System.out.println("Finish CLI push...");
                System.out.println("OUT: " + output.toString());
                System.out.println("CLI push successful!");
            } else {
                System.out.println("CLI push failed! (exitVal != 0)");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("ERR");
            throw new RuntimeException(e);
        }
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
