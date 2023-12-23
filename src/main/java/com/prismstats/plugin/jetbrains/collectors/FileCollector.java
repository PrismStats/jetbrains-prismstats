package com.prismstats.plugin.jetbrains.collectors;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.prismstats.plugin.jetbrains.PrismStats;
import net.minidev.json.JSONObject;
import org.jetbrains.annotations.Nullable;

public class FileCollector {
    public static JSONObject jsonObject = new JSONObject();

    public static void addFile(DocumentEvent documentEvent) {
        @Nullable VirtualFile file = PrismStats.getFile(documentEvent.getDocument());

        assert file != null;

        JSONObject files = new JSONObject();
        files.put("name", file.getName());
        files.put("path", file.getPath());
        files.put("type", PrismStats.getLanguage(file));
        files.put("lines", documentEvent.getDocument().getLineCount());
        files.put("characters", documentEvent.getDocument().getTextLength());

        jsonObject.put(file.getPath(), files);
    }

    public static JSONObject getData() {
        return jsonObject;
    }

    public static void clearData() {
        jsonObject.clear();
    }
}
