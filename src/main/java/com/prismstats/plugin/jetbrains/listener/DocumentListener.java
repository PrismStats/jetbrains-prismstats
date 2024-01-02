package com.prismstats.plugin.jetbrains.listener;

import com.intellij.openapi.editor.event.BulkAwareDocumentListener;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.project.Project;
import com.prismstats.plugin.jetbrains.PrismStats;
import com.prismstats.plugin.jetbrains.collectors.DataCollector;
import com.prismstats.plugin.jetbrains.collectors.FileCollector;
import com.prismstats.plugin.jetbrains.collectors.ProjectCollector;
import org.jetbrains.annotations.NotNull;
public class DocumentListener implements BulkAwareDocumentListener.Simple {

    @Override
    public void documentChangedNonBulk(@NotNull DocumentEvent documentEvent) {
        documentEvent.getOldFragment().length();

        try {
            Project project = PrismStats.getProject(documentEvent.getDocument());
            if(project == null) return;

            FileCollector.addFile(documentEvent);
            ProjectCollector.addProject(project);
            ProjectCollector.addProjectFile(project, documentEvent);

            if(documentEvent.getOldFragment().toString().isEmpty()) {
                // CHAR ADDED
                DataCollector.addChar(documentEvent.getNewFragment().toString().length());
            } else if(documentEvent.getNewFragment().toString().isEmpty()) {
                // CHAR REMOVED
                DataCollector.removeChar(documentEvent.getOldFragment().toString().length());
            }

            if(documentEvent.getNewFragment().toString().equals("\n")) {
                // LINE ADDED
                DataCollector.addLine(1);
            } else if(documentEvent.getOldFragment().toString().equals("\n")) {
                // LINE REMOVED
                DataCollector.removeLine(1);
            }

        } catch (Exception e) {
            System.out.println("Error while collecting data: " + e.getMessage());
        }
    }
}