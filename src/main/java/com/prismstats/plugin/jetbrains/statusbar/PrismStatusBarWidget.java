package com.prismstats.plugin.jetbrains.statusbar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.WindowManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class PrismStatusBarWidget implements StatusBarWidget {
    public final Project project;
    public final StatusBar statusBar;

    public PrismStatusBarWidget(Project project) {
        this.project = project;
        this.statusBar = WindowManager.getInstance().getStatusBar(project);
    }

    @Override
    public @NonNls @NotNull String ID() {
        return "PrismStats";
    }

    public WidgetPresentation getPresentation() {
        return new PrismStatusBarPresenter(this);
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {}

    @Override
    public void dispose() {

    }
}
