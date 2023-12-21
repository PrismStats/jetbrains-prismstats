package com.prismstats.plugin.jetbrains.listener;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManagerListener;

public class DocumentListener implements FileDocumentManagerListener {

    @Override
    public void beforeDocumentSaving(Document document) {
        try {
            System.out.println("DocumentListener: beforeDocumentSaving");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
