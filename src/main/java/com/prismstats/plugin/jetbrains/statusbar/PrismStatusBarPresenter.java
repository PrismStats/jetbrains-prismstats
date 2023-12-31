package com.prismstats.plugin.jetbrains.statusbar;

import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidget.Multiframe;
import com.intellij.openapi.wm.StatusBarWidget.MultipleTextValuesPresentation;
import com.intellij.util.Consumer;
import com.prismstats.plugin.jetbrains.PrismIcons;
import com.prismstats.plugin.jetbrains.PrismStats;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class PrismStatusBarPresenter implements MultipleTextValuesPresentation, Multiframe {

    private final PrismStatusBarWidget widget;

    public PrismStatusBarPresenter(PrismStatusBarWidget widget) {
        this.widget = widget;
    }

    @Override
    public StatusBarWidget copy() {
        return new PrismStatusBarWidget(this.widget.project);
    }

    @Override
    public @NonNls @NotNull String ID() {
        return "PrismStats";
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {}

    @Override
    public void dispose() {
        Disposer.dispose(widget);
    }

    @Override
    public @Nullable ListPopup getPopupStep() {
        PrismStats.openDashboard();
        PrismStats.updateStatusBarText();
        return null;
    }

    @Override
    public @Nullable String getSelectedValue() {
        return PrismStats.getStatusBarText();
    }

    @Override
    public @Nullable Icon getIcon() {
        return UIManager.getLookAndFeel().getName().equals("Darcula") ? PrismIcons.PrismDark : PrismIcons.PrismLight;
    }

    @Override
    public @Nullable String getTooltipText() {
        return "Your today's code time";
    }

    @Override
    public @Nullable Consumer<MouseEvent> getClickConsumer() {
        return null;
    }
}
