package com.prismstats.plugin.jetbrains.settingsmenu;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

public class CustomSettingsDialog extends DialogWrapper {
    private final JPanel jPanel;
    private final JLabel apiKeyLabel;
    private final JTextField apiKeyTextField;

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

        init();
    }

    @Nullable @Override
    protected JComponent createCenterPanel() {
        return jPanel;
    }
}
