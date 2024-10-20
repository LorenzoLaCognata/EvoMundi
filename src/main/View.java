package main;

import javafx.scene.layout.BorderPane;
import view.CenterPaneManager;
import view.StatusBarManager;
import view.ToolBarManager;

public class View {

    private final ToolBarManager toolBarManager = new ToolBarManager();
    private final StatusBarManager statusBarManager = new StatusBarManager();
    private final CenterPaneManager centerPaneManager = new CenterPaneManager();
    private final BorderPane borderPane = new BorderPane();

    public View() {
        borderPane.setTop(toolBarManager.getToolBar());
        borderPane.setBottom(statusBarManager.getStatusBar());
        borderPane.setCenter(centerPaneManager.getCenterPane());
    }

    public ToolBarManager getToolBarManager() {
        return toolBarManager;
    }

    public StatusBarManager getStatusBarManager() {
        return statusBarManager;
    }

    public CenterPaneManager getCenterPaneManager() {
        return centerPaneManager;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

}
