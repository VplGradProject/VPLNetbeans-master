/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class ProjectScrollAnchorPane extends ScrollAnchorPane {

    public ProjectScrollAnchorPane() {
        super();
    }

    public ProjectScrollAnchorPane(Component parent) {
        super(parent);
    }

    @Override
    protected void drawChildren() {

        ObservableList<Component> lst = getParentComponent().childernProperty();
        ((AnchorPane) getContent()).getChildren().clear();
        double y = 20;
        double x = 150;
        for (Component c : lst) {
            Node n = c.getUiDelegate();
            ((AnchorPane) getContent()).getChildren().add(n);
            n.setLayoutX(x);
            n.setLayoutY(y);
            y += n.getBoundsInParent().getHeight() + 50;
        }
        
    }

}
