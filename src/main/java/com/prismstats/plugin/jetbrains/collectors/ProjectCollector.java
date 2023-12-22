package com.prismstats.plugin.jetbrains.collectors;

import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.prismstats.plugin.jetbrains.PrismStats;
import net.minidev.json.JSONObject;
import org.jetbrains.annotations.Nullable;


public class ProjectCollector {
    public static JSONObject jsonObject = new JSONObject();
    public static JSONObject projectObject = new JSONObject();

    public static void addProject(Project project) {
        jsonObject.put(project.getName() + "::NAME", project.getName());

        System.out.println(jsonObject.toJSONString());
    }

    public static void addFile(Project project, DocumentEvent documentEvent) {
        @Nullable VirtualFile file = PrismStats.getFile(documentEvent.getDocument());

        assert file != null;

        projectObject.put(file.getNameSequence() + "::LINES", documentEvent.getDocument().getLineCount());
        projectObject.put(file.getNameSequence() + "::CHARACTERS", documentEvent.getDocument().getTextLength());
        projectObject.put(file.getNameSequence() + "::TYPE", file.getFileType());

        jsonObject.put(project.getName() + "::FILES", projectObject);
    }

    public static JSONObject getData() {
        return jsonObject;
    }

    public static void clearData() {
        jsonObject.clear();
    }
}
