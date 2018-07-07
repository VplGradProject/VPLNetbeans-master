/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler;
import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.javafx.collections.ObservableMapWrapper;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Shoka
 */
public class ComponentsModel {

    private ObservableMap<String, ProjectComponent> model;
    private StringProperty currentComponent; //TODO DONE BUT NEEDS FIX add Current Component Selector
    private static ComponentsModel instance = new ComponentsModel();
    private ObjectProperty<TreeItem<Component>> root;

    private ComponentsModel() {
        this.model = new ObservableMapWrapper<>(new HashMap<>());
        this.currentComponent = new SimpleStringProperty();
        root = new SimpleObjectProperty<>();
        model.addListener((Observable e) -> {
            calculateRoot();
        });
    }

    public static ComponentsModel getInstance() {
        return instance;
    }

    public Component getComponentByUuid(String uuid) throws ComponentNotFoundException {
        ProjectComponent comp = model.get(uuid);
        if (comp != null) {
            return comp;
        }
        ObservableList<ProjectComponent> lst = toList();
        for (ProjectComponent c : lst) {
            try {
                Component f = c.getComponentByUuid(uuid);
                if (f != null) {
                    return f;
                }
            } catch (ComponentNotFoundException ex) {

            }

        }
        throw new ComponentNotFoundException();
    }
//    public List<Component> getComponentsByProperty(Component c, String prorpetyName, String value){
//        List<Component> components = new ArrayList<>();
//        c.getPropreties().get(prorpetyName);
//     TODO add getComponentsByProperty   
//    }

    public String createLink(String firstUuid, String lastUuid) throws ComponentNotFoundException {
        Component firstComp = getComponentByUuid(firstUuid);
        Component lastComp = getComponentByUuid(lastUuid);
        Component parent = firstComp.getParent();
        int indx = parent.childernProperty().indexOf(firstComp);
        parent.childernProperty().add(indx, lastComp);
        return lastComp.getUUID();
    }

    public List<Component> getComponentsByType(String type) {
        List<Component> components = new ArrayList<>();
        ObservableList<ProjectComponent> temp = toList();
        Queue<Component> q = new ArrayDeque<>();
        q.addAll(temp);
        while (!q.isEmpty()) {
            Component c = q.poll();
            if (c == null) {
                continue;
            }
            if (c.getType().equals(type)) {
                components.add(c);
            }
            q.addAll(c.childernProperty());
        }

        return components;
    }

    public String addProject(String projectName) {
        ProjectComponent c = new ProjectComponent(projectName);
        return addProject(c);
    }

    public String addProject(ProjectComponent c) {
        model.put(c.getUUID(), c);
        addComponentHelper(c);

        return c.getUUID();
    }

    public String addComponent(String uuid, Component c) throws ComponentNotFoundException, IllegalComponent {
        Component parent = getComponentByUuid(uuid);
        if (parent == null) {
            throw new ComponentNotFoundException();
        }
        parent.addComponent(c);
        addComponentHelper(c);
        return c.getUUID();
    }

    public String addComponent(String uuid, Component c, int index) throws ComponentNotFoundException, IllegalComponent {
        Component parent = getComponentByUuid(uuid);
        if (parent == null) {
            throw new ComponentNotFoundException();
        }
        addComponentHelper(c);
        parent.addComponent(index, c);
        return c.getUUID();
    }

    protected void addComponentHelper(Component c) {
        c.childernProperty().addListener((Observable e) -> {
            ComponentsModel.getInstance().calculateRoot();//FIXME calculate root 
        });
        if ((c.getFlags() & ComponentFlags.PAGEABLE_FLAG) > 0) {
            Pageable p = (Pageable) c;
            UiManager.getInstance().addTab(p.getTab());
        }
        c.getUiDelegate().setFocusTraversable(true);
        c.getUiDelegate().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    c.getUiDelegate().requestFocus();
                }
            });
        });
        if ((c.getFlags() & ComponentFlags.DRAGGABLE_FLAG) > 0) {
            DraggableComponent uiDelegate = (DraggableComponent) c.getUiDelegate();
            ContextMenu cx = uiDelegate.getMenu();
            cx.getItems().addAll(DragContextMenuItems.createContextMenu(c));
            c.getUiDelegate().addEventHandler(MouseEvent.DRAG_DETECTED, new DraggableCanvasComponentEventHandler());
        }

    }

    public List<Component> getComponentTreeByUuid(String uuid) throws ComponentNotFoundException {
        Queue<Component> compq = new LinkedList<>();
        List<Component> lst = new ArrayList<>();
        Component comp = getComponentByUuid(uuid);
        compq.add(comp);
        while (!compq.isEmpty()) {
            Component c = compq.poll();
            lst.add(c);
            compq.addAll(c.childernProperty());
        }
        return lst;

    }

    public boolean removeComponetByUuid(String uuid) throws ComponentNotFoundException {
        //TODO DONE add remove by finding
        List<Component> lst = getComponentTreeByUuid(uuid);
        for (Component c : lst) {
            removeSingleComponet(c);
        }
        return true;
    }

    public boolean removeSingleComponet(Component c) throws ComponentNotFoundException {
        removeComponentHelper(c);
        Component parent = c.getParent();
        if (parent != null) {
            parent.childernProperty().remove(c);
        }
        return true;
    }

    public boolean removeSingleComponetByUuid(String uuid) throws ComponentNotFoundException {
        //TODO DONE add remove by finding
        Component c = getComponentByUuid(uuid);
        return removeSingleComponet(c);
    }

    public ObservableList<MethodComponent> getAllMethods() {
        ObservableList<MethodComponent> lst = new ObservableListWrapper<>(new ArrayList<>());
        for (String s : model.keySet()) {
            ProjectComponent get = model.get(s);
            lst.addAll(get.getAllMethods());
        }
        return lst;
    }

    public ObservableList<MethodComponent> getClassMethods(String uuid) throws ComponentNotFoundException {
        ClassComponent comp = (ClassComponent) getComponentByUuid(uuid);
        ObservableList<MethodComponent> lst = new ObservableListWrapper<>(new ArrayList<>());
        lst.setAll(comp.getMethodsList());
        return lst;
    }

    public ClassComponent getClassByName(String name) throws ComponentNotFoundException {
        ObservableList<ProjectComponent> lst = toList();
        for (ProjectComponent c : lst) {
            //FIXME fix return of class name
            return c.getClassByName(name);
        }

        throw new ComponentNotFoundException("Class Not Found");
    }

    public String addMethodByUuid(String uuid, MethodComponent c) throws IllegalComponent, ComponentNotFoundException {
        try {
            return addComponent(uuid, c);
        } catch (NullPointerException ex) {
            throw new ComponentNotFoundException();
        }

    }

    public ObjectProperty<TreeItem<Component>> treeItemsProperty() {
        return root;
    }

    public void calculateRoot() {
        ObservableList<ProjectComponent> lst = toList(); //FIXME change project name
        root.setValue(new TreeItem<>(new SimpleComponent()));
        for (Component c : lst) {
            root.get().getChildren().add(calculateRootHelper(c));
        }
    }

    private TreeItem<Component> calculateRootHelper(Component c) {
        TreeItem<Component> root = new TreeItem<>(c);
        if (c.childernProperty().isEmpty()) {
            return root;
        } else {
            for (Component child : c.childernProperty()) {
                root.getChildren().add(calculateRootHelper(child));
            }
            return root;
        }
    }

    public ObservableList<ProjectComponent> toList() {
        return FXCollections.observableArrayList(model.values());
    }

    public ObservableMap<String, ProjectComponent> getModel() {
        return model;
    }

    public void setModel(ObservableMap<String, ProjectComponent> model) {
        this.model = model;
    }

    public StringProperty currentComponentProperty() {
        return currentComponent;
    }

    public String getCurrentComponent() {
        return currentComponent.getValue();
    }

    public void setCurrentComponent(StringProperty currentComponent) {
        this.currentComponent = currentComponent;
    }

    public void setCurrentComponent(String currentComponent) {
        this.currentComponent.setValue(currentComponent);
    }

    private void removeComponentHelper(Component c) {
        if ((c.getFlags() & ComponentFlags.PAGEABLE_FLAG) > 0) {
            Pageable p = (Pageable) c;
            UiManager.getInstance().removeTab(p.getTab());
        }

    }

    public void insertComponentAfter(Component prev, Component c) throws ComponentNotFoundException, IllegalComponent {

        insertComponentHelper(prev, c, 1);
    }

    public void insertComponentBefore(Component prev, Component c) throws ComponentNotFoundException, IllegalComponent {
        insertComponentHelper(prev, c, -1);

    }

    private void insertComponentHelper(Component prev, Component c, int offset) throws ComponentNotFoundException, IllegalComponent {
        Component parent = prev.getParent();
        int i = parent.childernProperty().indexOf(prev);
        addComponent(parent.getUUID(), c, i + offset);
    }
}
