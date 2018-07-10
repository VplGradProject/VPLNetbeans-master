/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler;
import com.icraus.vpl.codegenerator.parsers.PropertiesParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.StrokeLineJoin;

public class ProjectScrollAnchorPane extends ScrollAnchorPane {

    ProjectComponent parent;
    Map<String, Node> compMap = new HashMap<>();
    Map<String, String> definedClasses = new HashMap<>();

    public ProjectScrollAnchorPane() {
        super();
    }

    public ProjectScrollAnchorPane(Component _parent) {
        super(_parent);
        parent = (ProjectComponent) _parent;

    }

    @Override
    protected void drawChildren() {

        ObservableList<Component> lst = parent.childernProperty();
        ContentPane pane = (ContentPane) getContent();
        pane.getChildren().clear();

        List<Result> resultLst = new ArrayList<>();
        for (Component c : lst) {
            ClassComponent tmp = (ClassComponent) c;
            Result rs = getInheritance(tmp);
            resultLst.add(rs);

            addToPane(pane, compMap.get(rs.parentClass));
//            setLayOutXY(compMap.get(rs.parentClass), x1, y - 50);

            for (String n : rs.interfaces) {
                addToPane(pane, compMap.get(n));
//                setLayOutXY(compMap.get(n), x1 + 50, y - 50);

            }
            addToPane(pane, compMap.get(rs.currentClass));
//            setLayOutXY(compMap.get(rs.currentClass), x, y);
//            y += compMap.get(rs.currentClass).getBoundsInParent().getHeight() + 50;
        }
        if (resultLst.isEmpty()) {
            return;
        }
        for (Result r : resultLst) {

            creatLine(pane, compMap.get(r.parentClass), compMap.get(r.currentClass));

            for (String n : r.interfaces) {
                creatLine(pane, compMap.get(n), compMap.get(r.currentClass));

            }

        }
    }

    private void creatLine(Pane pane, Node prev, Node nxt) {
        Line l = new Line();
        if (prev == null || nxt == null) {
            return;
        }
        l.startXProperty().bind(prev.layoutXProperty().add(prev.getBoundsInParent().getWidth() / 2.0));
        l.startYProperty().bind(prev.layoutYProperty().add(prev.getBoundsInParent().getHeight()));
        l.endXProperty().bind(nxt.layoutXProperty().add(nxt.getBoundsInParent().getWidth() / 2.0));
        l.endYProperty().bind(nxt.layoutYProperty());
        l.setStrokeWidth(10);
        l.setStrokeLineJoin(StrokeLineJoin.MITER);
        pane.getChildren().add(l);
    }

    private Result getInheritance(ClassComponent tmp) {
        Result r = new Result();

        String className = tmp.propertyMapProperty().get("%%class name%%");
        className += tmp.getUUID();
        r.currentClass = className;
        compMap.putIfAbsent(className, tmp.getUiDelegate());

        String extendedClass = tmp.propertyMapProperty().get("%%extends%%");
        extendedClass = PropertiesParser.formatLookAHeadInput("D%<{$$EXTENDS_WRD}<%", extendedClass).trim();
        String interfaces = tmp.propertyMapProperty().get("%%interfaces%%[]");
        interfaces = PropertiesParser.formatLookAHeadInput("D%<{$$IMPLEMENTS_WRD}<%", interfaces);
        if (!extendedClass.isEmpty()) {
            try {
                ClassComponent superClass = parent.getClassByName(extendedClass);
                extendedClass += superClass.getUUID();
                compMap.putIfAbsent(extendedClass, superClass.getUiDelegate());
                r.parentClass = extendedClass;
            } catch (ComponentNotFoundException ex) {
                Button b = new Button("class: " +extendedClass);
                compMap.putIfAbsent(extendedClass, b);
                b.addEventHandler(MouseEvent.DRAG_DETECTED, new DraggableCanvasComponentEventHandler());
                r.parentClass = extendedClass;
            }
        }
        if (!interfaces.isEmpty()) {
            String[] split = interfaces.split(",");
            ClassComponent interfaceClass = null;
            for (String v : split) {
                try {
                    interfaceClass = parent.getClassByName(v);
                    r.interfaces.add(v);
                    compMap.putIfAbsent(v, interfaceClass.getUiDelegate());
                } catch (ComponentNotFoundException | NullPointerException ex) {
                    Button b = new Button("interface: " + v);
                    compMap.putIfAbsent(v, b);
                    b.addEventHandler(MouseEvent.DRAG_DETECTED, new DraggableCanvasComponentEventHandler());
                    r.interfaces.add(v);
                }
            }
        }

        return r;
    }

    private void addToPane(Pane p, Node n) {

        if (n == null || p.getChildren().contains(n)) {
            return;
        } else {
            p.getChildren().add(n);
        }
    }

    private void setLayOutXY(Node get, double x, double y) {
        if (get != null) {
            get.setLayoutX(x);
            get.setLayoutY(y);
        }
    }

    public void redraw() {
        drawChildren();
    }

    static class Result {

        String currentClass = "";
        String parentClass = "";
        List<String> interfaces = new ArrayList<>();
    }
}
