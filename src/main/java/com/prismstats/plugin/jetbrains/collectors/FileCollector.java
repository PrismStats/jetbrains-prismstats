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

        jsonObject.put(file.getNameSequence() + "::LINES", documentEvent.getDocument().getLineCount());
        jsonObject.put(file.getNameSequence() + "::CHARACTERS", documentEvent.getDocument().getTextLength());
        jsonObject.put(file.getNameSequence() + "::TYPE", file.getFileType());

        System.out.println(jsonObject.toJSONString());
    }

    public static JSONObject getData() {
        return jsonObject;
    }

    public static void clearData() {
        jsonObject.clear();
    }
}
