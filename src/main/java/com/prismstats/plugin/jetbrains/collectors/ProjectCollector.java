package com.prismstats.plugin.jetbrains.collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.prismstats.plugin.jetbrains.PrismStats;
import org.jetbrains.annotations.Nullable;

import java.util.stream.StreamSupport;

public class ProjectCollector {
    public static JsonArray projectArray = new JsonArray();

    public static void addProject(Project project) {
        if(projectArray.isEmpty()) {
            JsonObject newProject = new JsonObject();
            newProject.addProperty("name", project.getName());
            newProject.addProperty("path", project.getBasePath());
            projectArray.add(newProject);
            return;
        }

        for (int i = 0; i < projectArray.size(); i++) {
            JsonObject projectObject = projectArray.get(i).getAsJsonObject();

            if (!projectObject.get("path").getAsString().equals(project.getBasePath())) {
                JsonObject newProject = new JsonObject();
                newProject.addProperty("name", project.getName());
                newProject.addProperty("path", project.getBasePath());
                projectArray.add(newProject);
            }
        }
    }

    public static void addProjectFile(Project project, DocumentEvent documentEvent) {
        @Nullable VirtualFile file = PrismStats.getFile(documentEvent.getDocument());

        if(file == null) return;

        for (int i = 0; i < projectArray.size(); i++) {
            if(!projectArray.get(i).getAsJsonObject().get("path").getAsString().equals(project.getBasePath())) return;

            JsonObject projectObject = projectArray.get(i).getAsJsonObject();

            if(!projectObject.has("files")) {
                JsonArray projectFilesArray = new JsonArray();
                projectFilesArray.add(file.getPath());
                projectObject.add("files", projectFilesArray);
            }

            JsonArray projectFilesArray = projectObject.get("files").getAsJsonArray();

            String filePath = file.getPath();
            boolean isFileExist = StreamSupport.stream(projectFilesArray.spliterator(), false)
                    .anyMatch(jsonElement -> jsonElement.getAsString().equals(filePath));

            if (!isFileExist) {
                projectFilesArray.add(filePath);
            }
        }
    }

    public static JsonArray getData() {
        return projectArray;
    }

    public static void clearData() {
        projectArray = new JsonArray();
    }
}
