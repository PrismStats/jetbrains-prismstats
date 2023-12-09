package com.prismstats.plugin.jetbrains;

import com.intellij.ProjectTopics;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.project.Project;

public class CustomSettingsMenu extends AnAction {
    public CustomSettingsMenu() {
        super("PrismStats Settings");
    }

    @Override
    public void actionPerformed(com.intellij.openapi.actionSystem.AnActionEvent e) {
        Project project = e.getProject();
        CustomSettingsDialog settingsDialog = new CustomSettingsDialog(project);
        settingsDialog.show();
    }
}
