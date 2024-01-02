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
                DataCollector.addChar(documentEvent.getNewFragment().toString()
                        .replaceAll(" ", "")
                        .replaceAll("\n", "")
                        .length());
            } else if(documentEvent.getNewFragment().toString().isEmpty()) {
                // CHAR REMOVED
                DataCollector.removeChar(documentEvent.getOldFragment().toString()
                        .replaceAll(" ", "")
                        .replaceAll("\n", "")
                        .length());
            }

            String[] newLines = documentEvent.getNewFragment().toString().split("\n", -1);
            int newLineCount = newLines.length - 1;
            System.out.println("A: " + newLineCount);
            DataCollector.addLine(newLineCount);

            String[] oldLines = documentEvent.getOldFragment().toString().split("\n", -1);
            int removedLineCount = oldLines.length - 1;
            System.out.println("R : "+ removedLineCount);
            DataCollector.removeLine(removedLineCount);
        } catch (Exception e) {
            System.out.println("Error while collecting data: " + e.getMessage());
        }
    }
}