package com.prismstats.plugin.jetbrains;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class CustomSettingsDialog extends DialogWrapper {
    private final JPanel jPanel;
    private final JLabel apiKeyLabel;
    private final JTextField apiKeyTextField;
    private final JLabel statusBarTypeLabel;
    private final JList statusBarType;

    public CustomSettingsDialog(@Nullable Project project) {
        super(project, true);

        setTitle("PrismStats Settings");
        setOKButtonText("Save");

        jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(2, 1));

        apiKeyLabel = new JLabel("API Key");
        jPanel.add(apiKeyLabel);

        apiKeyTextField = new JTextField(20);
        jPanel.add(apiKeyTextField);

        statusBarTypeLabel = new JLabel("Status Bar Type");
        jPanel.add(statusBarTypeLabel);

        statusBarType = new JList(new String[] {"Today's Code Time", "Today's Code Time in IDE", "Today's Code Time in Project"});
        statusBarType.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jPanel.add(statusBarType);

        init();
    }

    @Nullable @Override
    protected JComponent createCenterPanel() {
        return jPanel;
    }
}
