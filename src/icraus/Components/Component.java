/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.Statement;
import com.sun.javafx.collections.ObservableListWrapper;
import com.sun.javafx.collections.ObservableMapWrapper;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public abstract class Component implements Cloneable {

    private static final long serialVersionUID = 1L;
//TODO set getters and remove setters of props to properties
    //TODO change it to use Visitor Pattern
//TODO DONE add Component Type
    //TODO add xml serializer for saving and closing
    private StringProperty uuid;
    private ObjectProperty<SimplePropertyStatement> statement;
    //TODO CHANGE Component Ui Refrence to ID;
    private ObjectProperty<Node> uiDelegate;
    private ObservableList<Component> childern;
    private ObjectProperty<Component> parent;
    private String type = "";
    private int flags = ComponentFlags.INSERTABLE_FLAG | ComponentFlags.SELECTABLE_FLAG | ComponentFlags.REMOVABLE_FLAG;
    private ObservableMap<String, String> propertyMap;

    private ObjectProperty<UiProperties> uiProperties;

    public ObjectProperty<UiProperties> uiPropertiesProperty() {
        return uiProperties;
    }

    public UiProperties getUiProperties() {
        return uiProperties.get();
    }

    public void setUiProperties(UiProperties properties) {
        this.uiProperties.set(properties);
    }

    public ObservableList<Component> childernProperty() {
        return childern;
    }

    @XmlElementWrapper(name = "componentChildern")
    @XmlIDREF
    public LinkedList<Component> getChildern() {
        return new LinkedList<>(childern);
    }

    public void setChildern(LinkedList<Component> arr) {
        childernProperty().clear();
        childernProperty().addAll(arr);
    }

    public Component() {
        this.uiProperties = new SimpleObjectProperty<>(new UiProperties());
        this.propertyMap = new ObservableMapWrapper<>(new HashMap<>());
        statement = new SimpleObjectProperty<>(new SimplePropertyStatement());
        uiDelegate = new SimpleObjectProperty<>();
        uuid = new SimpleStringProperty();
        parent = new SimpleObjectProperty<>();
        childern = new ObservableListWrapper<>(new LinkedList<>());
        uuid.setValue(getType() + UUID.randomUUID().toString());
        createListenrs();
    }

    @Override
    public abstract String toString();

    public String getType() {
        return type;
    }

    public void setType(String s) {
        type = s;
    }

    public int getFlags() { //TODO migrate to flags
        return flags;
    }

    public void setFlags(int i) {
        flags = i;
    }

    public ObjectProperty<SimplePropertyStatement> statementProperty() {
        return statement;
    }

    @XmlElementRef
    public SimplePropertyStatement getStatement() {
        return statement.get();
    }

    public void setStatement(SimplePropertyStatement st) {
        statement.setValue(st);
    }

    public ObjectProperty<Node> uiDelegateProperty() {
        return uiDelegate;
    }

    @XmlTransient
    public Node getUiDelegate() {
        return uiDelegate.get();
    }

    public void setUiDelegate(Node ui) {
        uiDelegate.setValue(ui);
    }

    public void setUUID(String s) {
        uuid.set(s);
    }

    @XmlID
    public String getUUID() {
        return uuid.getValue();
    }

    public String addComponent(Component c) throws IllegalComponent {
        return addComponent(getChildern().size(), c);
    }

    public String addComponent(int index, Component c) throws IllegalComponent {
        List<String> allowedChildren = getStatement().getAllowedChildren();
        if (!allowedChildren.isEmpty()) {
            if (allowedChildren.contains(SimplePropertyStatement.__NONE__VAR) || (!allowedChildren.contains(c.getType()))) {
                throw new IllegalComponent();
            }
        }
        childernProperty().add(index, c);
        c.setParent(this);
        return c.getUUID();

    }

    public void removeComponent(String uuid) throws ComponentNotFoundException {
        for (Component c : childernProperty()) {
            if (c.getUUID() == uuid) {
                childernProperty().remove(c);
                return;
            }
        }
        throw new ComponentNotFoundException();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Component)) {
            return false;
        }

        Component tmp = (Component) obj;
        if (!this.getUUID().equals(tmp.getUUID())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    public ObjectProperty<Component> parentProperty() {
        return parent;
    }
//    @XmlElementRef

    @XmlIDREF
    public Component getParent() {
        return parent.getValue();
    }

    public void setParent(Component _parent) {
        this.parent.setValue(_parent);
    }

    public void moveUp() {
        moveHelper(-1);
    }

    public void moveDown() {
        moveHelper(1);

    }

    private void moveHelper(int offset) {
        Component p = getParent();
        int i = p.childernProperty().indexOf(this);
        if ((offset < 0 && i <= 0) || (offset > 0 && (i + 1) >= p.childernProperty().size())) {
            return;
        }
        p.childernProperty().remove(i);
        p.childernProperty().add(i + offset, this);
    }

    public Map<String, String> getPropertyMap() {
        return new HashMap<>(propertyMap);
    }

    public ObservableMap<String, String> propertyMapProperty() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, String> _propertyMap) {
        this.propertyMap.putAll(_propertyMap);
    }

    private void createListenrs() {
        propertyMapProperty().addListener((Observable e) -> {
            getStatement().setPropertyMap(propertyMap);
        });
        childernProperty().addListener((Observable e) -> {
            childrenChanged();
        });
    }

    private void childrenChanged() {
        List<Statement> _children = getStatement().getChildren();
        _children.clear();
        for (Component c : childernProperty()) {
            _children.add(c.getStatement());
        }
    }

    @Override
    public Component clone() throws CloneNotSupportedException {
        try {

            Component instance = getClass().newInstance();
            instance.propertyMapProperty().putAll(this.getPropertyMap());
            instance.setStatement(new SimplePropertyStatement(this.getStatement()));
            instance.setUiProperties(new UiProperties(getUiProperties()));
            return instance;
        } catch (InstantiationException ex) {
            Logger.getLogger(Component.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Component.class.getName()).log(Level.SEVERE, null, ex);
        }

        throw new CloneNotSupportedException();
    }

}
