package com.prismstats.plugin.jetbrains.collectors;

import com.google.gson.JsonArray;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.vfs.VirtualFile;
import com.prismstats.plugin.jetbrains.PrismStats;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

public class FileCollector {

    public static JsonArray files = new JsonArray();

    public static void addFile(DocumentEvent documentEvent) {
        @Nullable VirtualFile file = PrismStats.getFile(documentEvent.getDocument());
        assert file != null;

        boolean fileAlreadyExists = false;
        int fileIndex = -1;
        for (int i = 0; i < files.size(); i++) {
            JsonObject fileObject = files.get(i).getAsJsonObject();
            if (fileObject.get("path").getAsString().equals(file.getPath())) {
                fileAlreadyExists = true;
                fileIndex = i;
            };
        }

        if (fileAlreadyExists) files.remove(fileIndex);

        JsonObject fileObject = new JsonObject();
        fileObject.addProperty("name", file.getName());
        fileObject.addProperty("path", file.getPath());
        fileObject.addProperty("type", PrismStats.getLanguage(file));
        fileObject.addProperty("lines", documentEvent.getDocument().getLineCount());
        fileObject.addProperty("characters", documentEvent.getDocument().getTextLength());

        files.add(fileObject);
    }

    public static JsonArray getData() {
        return files;
    }

    public static void clearData() {
        files = new JsonArray();
    }
}
