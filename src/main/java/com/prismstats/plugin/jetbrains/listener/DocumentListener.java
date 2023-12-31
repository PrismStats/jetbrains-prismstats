package com.prismstats.plugin.jetbrains.listener;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.event.BulkAwareDocumentListener;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.project.Project;
import com.prismstats.plugin.jetbrains.PrismStats;
import com.prismstats.plugin.jetbrains.collectors.DataCollector;
import com.prismstats.plugin.jetbrains.collectors.FileCollector;
import com.prismstats.plugin.jetbrains.collectors.ProjectCollector;
import org.jetbrains.annotations.NotNull;

public class DocumentListener implements BulkAwareDocumentListener.Simple {

    public static int oldLineCount = 0;
    public static int oldCharCount = 0;

    @Override
    public void documentChangedNonBulk(@NotNull DocumentEvent documentEvent) {
        try {
            Project project = PrismStats.getProject(documentEvent.getDocument());

            if(project == null) return;

            Document document = documentEvent.getDocument();
            String newText = document.getText();
            String oldText = documentEvent.getOldFragment().toString();

            int newLineCount = newText.split("\n").length;
            int newCharCount = newText.length();

            int addedLines = newLineCount - oldLineCount;
            int removedLines = oldLineCount - newLineCount;

            int addedChars = newCharCount - oldCharCount;
            int removedChars = oldCharCount - newCharCount;

            if(addedLines > 0) {
                DataCollector.addLine(addedLines);
            } else if (removedLines > 0) {
                DataCollector.removeLine(removedLines);
            }

            if(addedChars > 0) {
                DataCollector.addChar(addedChars);
            } else if (removedChars > 0) {
                DataCollector.removeChar(removedChars);
            }

            oldLineCount = newLineCount;
            oldCharCount = newCharCount;

            FileCollector.addFile(documentEvent);
            ProjectCollector.addProject(project);
            ProjectCollector.addProjectFile(project, documentEvent);
        } catch (Exception e) {
            System.out.println("Error while collecting data: " + e.getMessage());
        }
    }
}