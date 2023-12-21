package com.prismstats.plugin.jetbrains.statusbar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PrismStatusBar implements StatusBarWidgetFactory {
    @Override
    public @NonNls @NotNull String getId() {
        return "PrismStatus";
    }

    @Override
    public @Nls @NotNull String getDisplayName() {
        return "PrismStats";
    }

    @Override
    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        return new PrismStatusBarWidget(project);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget statusBarWidget) {}

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return true;
    }
}
