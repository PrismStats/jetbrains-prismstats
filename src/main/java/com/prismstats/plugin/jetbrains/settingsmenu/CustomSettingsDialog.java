package com.prismstats.plugin.jetbrains.settingsmenu;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.prismstats.plugin.jetbrains.config.PrismConfig;
import com.prismstats.plugin.jetbrains.config.PrismConfigManager;
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

    @Override
    protected ValidationInfo doValidate() {
        if (apiKeyTextField.getText().isEmpty()) {
            return new ValidationInfo("API Key is required", apiKeyTextField);
        }

        if(!apiKeyTextField.getText().startsWith("ps_")) {
            return new ValidationInfo("Invalid API Key", apiKeyTextField);
        }

        return null;
    }

    @Override
    public void doOKAction() {
        PrismConfig config = PrismConfigManager.loadConfig();
        config.setKey(apiKeyTextField.getText());
        PrismConfigManager.saveConfig(config);
    }
}
