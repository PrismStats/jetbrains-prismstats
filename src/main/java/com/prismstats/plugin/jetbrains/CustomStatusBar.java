package com.prismstats.plugin.jetbrains;

import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.util.Consumer;
import com.intellij.util.ui.UIUtil;
import org.jetbrains.annotations.*;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class CustomStatusBar implements StatusBarWidgetFactory {
    @NotNull
    public String getId() {
        return "PrismStats";
    }

    @NotNull
    public String getDisplayName() {
        return "PrismStats";
    }

    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @NotNull
    public PrismStatsStatusBarWidget createWidget(@NotNull Project project) {
        return new PrismStatsStatusBarWidget(project);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) { }

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return true;
    }

    public class PrismStatsStatusBarWidget implements StatusBarWidget {
        public final Project project;
        public final StatusBar statusBar;

        @Contract(pure = true)
        public PrismStatsStatusBarWidget(@NotNull Project project) {
            this.project = project;
            this.statusBar = WindowManager.getInstance().getStatusBar(project);
        }

        @NotNull
        @Override
        public String ID() {
            return "PrismStats";
        }

        @Nullable
        @Override
        public WidgetPresentation getPresentation() {
            return new StatusBarPresenter(this);
        }

        @Override
        public void install(@NotNull StatusBar statusBar) { }

        @Override
        public void dispose() { }

        private class StatusBarPresenter implements StatusBarWidget.MultipleTextValuesPresentation, StatusBarWidget.Multiframe {
            private final PrismStatsStatusBarWidget widget;

            public StatusBarPresenter(PrismStatsStatusBarWidget widget) {
                this.widget = widget;
            }

            @Nullable
            @Override
            public ListPopup getPopupStep() {
                PrismStats.openDashboard();
                PrismStats.updateStatusBarText();
                return null;
            }

            @Nullable
            @Override
            public String getSelectedValue() { return PrismStats.getStatusBarText(); }

            @Override
            public @Nullable
            Icon getIcon() {
                if(UIUtil.isUnderDarcula()) {
                    return PrismIcons.PrismLight;
                } else {
                    return PrismIcons.PrismDark;
                }
            }

            @Nullable
            @Override
            public String getTooltipText() {
                return null;
            }

            @Nullable
            @Override
            public Consumer<MouseEvent> getClickConsumer() {
                return null;
            }

            @Override
            public StatusBarWidget copy() {
                return new PrismStatsStatusBarWidget(this.widget.project);
            }

            @Override
            public @NonNls
            @NotNull String ID() {
                return "PrismStats";
            }

            @Override
            public void install(@NotNull StatusBar statusBar) {

            }

            @Override
            public void dispose() {
                Disposer.dispose(widget);
            }
        }
    }
}
