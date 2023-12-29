package com.prismstats.plugin.jetbrains.listener;

import com.intellij.openapi.editor.event.BulkAwareDocumentListener;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.project.Project;
import com.prismstats.plugin.jetbrains.PrismStats;
import com.prismstats.plugin.jetbrains.collectors.FileCollector;
import com.prismstats.plugin.jetbrains.collectors.ProjectCollector;
import org.jetbrains.annotations.NotNull;

public class DocumentListener implements BulkAwareDocumentListener.Simple {

    @Override
    public void documentChangedNonBulk(@NotNull DocumentEvent documentEvent) {
        try {
            Project project = PrismStats.getProject(documentEvent.getDocument());

            if(project == null) return;

            FileCollector.addFile(documentEvent);
            ProjectCollector.addProject(project);
            ProjectCollector.addProjectFile(project, documentEvent);
        } catch (Exception e) {
            System.out.println("Error while collecting data: " + e.getMessage());
        }
    }
}
