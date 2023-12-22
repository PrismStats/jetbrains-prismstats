package com.prismstats.plugin.jetbrains.listener;

import com.intellij.openapi.editor.event.BulkAwareDocumentListener;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.project.Project;
import com.prismstats.plugin.jetbrains.PrismStats;
import com.prismstats.plugin.jetbrains.collectors.ProjectCollector;

public class DocumentListener implements BulkAwareDocumentListener.Simple {

    @Override
    public void documentChangedNonBulk(DocumentEvent documentEvent) {
        try {
            Project project = PrismStats.getProject(documentEvent.getDocument());

            assert project != null;

            ProjectCollector.addFile(project, documentEvent);
            ProjectCollector.addProject(project);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
