/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import icraus.Components.event.CanvasDragEventHandler;
import ide.ComponentClipBoard;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

/**
 *
 * @author Shoka
 */
public class ScrollAnchorPane extends ScrollPane implements Selectable {

    private Component parentComponent;

    public Component getParentComponent() {
        return parentComponent;
    }

    public void setParentComponent(Component parentComponent) {
        this.parentComponent = parentComponent;
        parentComponent.childernProperty().addListener((Observable e) -> {
            drawChildren();
        });
        setId("GUI" + parentComponent.getUUID());

    }

    public ScrollAnchorPane(Component _parent) {
        setPrefWidth(USE_PREF_SIZE);
        setPrefHeight(USE_PREF_SIZE);
        setFitToWidth(true);
        setFitToHeight(true);
        if (_parent != null) {
            initalize(_parent);
        }
        setContent(new ContentPane());
        getContent().addEventHandler(DragEvent.ANY, new CanvasDragEventHandler());
        MenuItem itm = new MenuItem("paste");
        ContextMenu m = new ContextMenu(itm);
        itm.setOnAction(e -> {
            try {
                ComponentClipBoard.getInstance().paste(getParentComponent());
            } catch (ComponentNotFoundException | CloneNotSupportedException | IllegalComponent ex) {
                Logger.getLogger(ScrollAnchorPane.class.getName()).log(Level.SEVERE, null, ex);
                new Alert(Alert.AlertType.ERROR, "Cannot paste here", ButtonType.OK).showAndWait();
            }
        });
        setContextMenu(m);
    }

    public ScrollAnchorPane() {
        this(null);
    }

    @Override
    public String getParentComponentUuid() {
        return parentComponent.getUUID();
    }

    protected void drawChildren() {

        ObservableList<Component> lst = parentComponent.childernProperty();
        ContentPane pane = (ContentPane) getContent();
        List<Node> oldNodeLst = new LinkedList<>(pane.getChildren());
        pane.getChildren().clear();
        double y = 50;
        double x = 200;
        List<Node> nodeLst = new ArrayList<>();
        for (Component c : lst) {
            Node n = c.getUiDelegate();
            nodeLst.add(n);
            if (!pane.getChildren().contains(n)) {
                pane.getChildren().add(n);
            }
            if (!oldNodeLst.contains(n)) {
                n.setLayoutX(x);
                n.setLayoutY(y);

            }
            y += n.getBoundsInParent().getHeight() + 50;
        }
        if (nodeLst.isEmpty()) {
            return;
        }
        Node prev = nodeLst.get(0);
        for (int i = 1; i < nodeLst.size(); ++i) {
            Node next = nodeLst.get(i);
            Line l = new ComponentLine(prev, next);
            pane.getChildren().add(l);
            prev = next;
        }
        pane.update();
    }

    @Override
    public void initalize(Component parent) {
        setParentComponent(parent);
    }
}

class ContentPane extends AnchorPane {

    public void update() {
        requestLayout();
        updateBounds();
        layoutChildren();
        setNeedsLayout(true);
        autosize();
        requestParentLayout();
        layout();

    }

    public ContentPane() {
        setWidth(2000);
        setHeight(2000);
        setPrefHeight(USE_PREF_SIZE);
        setPrefWidth(USE_PREF_SIZE);

    }

}
