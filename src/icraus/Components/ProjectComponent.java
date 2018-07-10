/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.sun.javafx.collections.ObservableListWrapper;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class ProjectComponent extends Component implements Pageable {

    private StringProperty projectName;
//    private ObservableMap<String, ClassComponent> model;

    public static final String PROJECT_TPYE = "PROJECT_TPYE";
    private ObjectProperty<Tab> projectMainTab;

    public ProjectComponent(String name) {
        super();
        projectName = new SimpleStringProperty();
        projectName.setValue(name);
        ScrollAnchorPane pane = new ProjectScrollAnchorPane(this);
        Tab tab = new Tab("", pane);
        setUiDelegate(pane);
        tab.textProperty().bindBidirectional(projectName);
        projectMainTab = new SimpleObjectProperty<>(tab);
//        this.model = new ObservableMapWrapper<>(new HashMap<>());

    }

    public ProjectComponent() {
        this("");
    }

    @Override
    public int getFlags() {
        return super.getFlags() | ComponentFlags.PAGEABLE_FLAG; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return projectName.getValue();
    }

    @Override
    public String getType() {
        return PROJECT_TPYE;
    }

    public Component getComponentByUuid(String uuid) throws ComponentNotFoundException {
        if (getUUID().equals(uuid)) {
            return this;
        }
//        ClassComponent comp = model.get(uuid);
//        if (comp != null) {
//            return comp;
//        }
        Queue<Component> q = new LinkedList<>();
        ObservableList<ClassComponent> lst = toList();
        q.addAll(lst);
        Component tmp = null;
        while (!q.isEmpty()) {
            Component top = q.poll();
            if (top.getUUID().equals(uuid)) {
                tmp = top;
                break;
            }
            q.addAll(top.childernProperty());
        }
        if (tmp == null) {
            throw new ComponentNotFoundException();
        }
        return tmp;
    }

    public String addClass(String className, String packageName) throws IllegalComponent {
        ClassComponent c = new ClassComponent(className, packageName);

        addComponent(c);
        return c.getUUID();
    }

    public String addComponent(String uuid, Component c) throws ComponentNotFoundException, IllegalComponent {
        Component parent = getComponentByUuid(uuid);
        if (parent == null) {
            throw new ComponentNotFoundException();
        }
        parent.addComponent(c);
        return parent.getUUID();
    }

    public boolean removeComponetByUuid(String uuid) throws ComponentNotFoundException {
        //TODO DONE add remove by finding
        ClassComponent comp = (ClassComponent) getComponentByUuid(uuid);
        if (comp != null) {
            return true;
        }
        return false;
    }

    public ObservableList<MethodComponent> getAllMethods() {
        ObservableList<MethodComponent> lst = new ObservableListWrapper<>(new ArrayList<>());
        ObservableList<ClassComponent> clst = toList();
        for (ClassComponent c : clst) {
            lst.addAll(c.getMethodsList());
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
        ObservableList<ClassComponent> lst = toList();
        for (ClassComponent c : lst) {
            if (c.propertyMapProperty().get("%%class name%%").equals(name)) {
                return c;
            }
        }
        throw new ComponentNotFoundException("Class Not Found");
    }

    public String addMethodByUuid(String uuid, MethodComponent c) throws IllegalComponent, ComponentNotFoundException {
        return getComponentByUuid(uuid).addComponent(c);

    }

    public ObservableList<ClassComponent> toList() {
        ObservableList<Component> lst = childernProperty();
        ObservableList<ClassComponent> clst = FXCollections.observableArrayList();
        for (Component c : lst) {
            clst.add((ClassComponent) c);
        }
        return clst;
    }

    @Override
    public String addComponent(Component c) throws IllegalComponent {
        if (c.getType().equals(ClassComponent.CLASS_TYPE)) {
            c.propertyMapProperty().addListener((Observable e) -> {
                ProjectScrollAnchorPane content = (ProjectScrollAnchorPane) getTab().getContent();
                content.redraw();
            });
            return super.addComponent(c);
        } //To change body of generated methods, choose Tools | Templates.
        throw new IllegalComponent("Not a Class Type");
    }

    @XmlTransient
    @Override
    public Tab getTab() {
        return projectMainTab.getValue();
    }

    public StringProperty projectNameProperty() {
        return projectName;
    }

    public String getProjectName() {
        return projectName.get();
    }

    public void setProjectName(String projectName) {
        this.projectName.setValue(projectName);
    }

}
