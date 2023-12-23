package com.prismstats.plugin.jetbrains.collectors;

import com.intellij.openapi.project.Project;
import net.minidev.json.JSONObject;


public class ProjectCollector {
    public static JSONObject jsonObject = new JSONObject();

    public static void addProject(Project project) {
        JSONObject projectObject = new JSONObject();
        projectObject.put("name", project.getName());
        projectObject.put("path", project.getBasePath());
        projectObject.put("files", FileCollector.getData());

        jsonObject.put(project.getName(), projectObject);
    }

    public static JSONObject getData() {
        return jsonObject;
    }

    public static void clearData() {
        jsonObject.clear();
    }
}
