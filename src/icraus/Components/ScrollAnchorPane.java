/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import icraus.Components.event.CanvasDragEventHandler;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
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
        pane.getChildren().clear();
        double y = 50;
        double x = 200;
        List<Node> nodeLst = new ArrayList<>();
        for (Component c : lst) {
            Node n = c.getUiDelegate();
            nodeLst.add(n);
            pane.getChildren().add(n);
            n.setLayoutX(x);
            n.setLayoutY(y);
            y += n.getBoundsInParent().getHeight() + 50;
        }
        if(nodeLst.isEmpty())
            return;
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
