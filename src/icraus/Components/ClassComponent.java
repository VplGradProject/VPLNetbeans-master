/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.IllegalStatementException;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.Statement;
import com.sun.javafx.collections.ObservableListWrapper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class ClassComponent extends Component {

    public static final String CLASS_TYPE = "CLASS_TYPE";
    private StringProperty className;
    private StringProperty packageName;
    private ObservableList<MethodComponent> methodsList;
    private ObservableList<SimpleComponent> fieldsList;
    private List<Statement> childStaments;
    private SimplePropertyStatement statementRef;

    public ClassComponent() {
        super();
        
        className = new SimpleStringProperty("");
        packageName = new SimpleStringProperty("");
        methodsList = new ObservableListWrapper<>(new ArrayList<>());
        fieldsList = new ObservableListWrapper<>(new ArrayList<>());

        try {
            statementRef = SimplePropertyStatement.fromFile("CLASS_TEMPLATE.template");
            propertyMapProperty().putAll(statementRef.getPropertyMap());
            childStaments = statementRef.getChildren();
            setStatement(statementRef);
        } catch (IllegalStatementException ex) {
            Logger.getLogger(ClassComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
        setUiDelegate(new ClassContentPane(this));
        createListners();
    }

    public ClassComponent(String className, String packageName) {
        this();
        this.className.setValue(className);
        this.packageName.setValue(packageName);

    }

    @Override
    public int getFlags() {
        return super.getFlags() | ComponentFlags.DRAGGABLE_FLAG; //To change body of generated methods, choose Tools | Templates.
    }

    protected void createListners() {
        propertyMapProperty().addListener((Observable e) ->{
            statementRef.setPropertyMap(getPropertyMap());
            className.setValue(propertyMapProperty().get("%%class name%%"));
            packageName.setValue(propertyMapProperty().get("%%packageName%%"));
        });
        className.addListener(e ->{
            statementRef.getPropertyMap().put("%%class name%%",className.get());
        });
        packageName.addListener(e ->{
            statementRef.getPropertyMap().put("%%packageName%%",packageName.get());
        });
        methodsList.addListener((Observable e) -> {
            componentChanged();

        });
        fieldsList.addListener((Observable e) -> {
            componentChanged();
        });
        

    }

    //TODO Replace it with IOC
    private void childrenChanged() {
        childernProperty().clear();
        childernProperty().addAll(fieldsList);
        childernProperty().addAll(methodsList);
    }


    public String getClassName() {
        return className.get();
    }

    public StringProperty classNameProperty() {
        return className;
    }

    public void setClassName(String v) {
        this.className.setValue(v);
    }

    public String getPackageName() {
        return packageName.get();
    }

    public StringProperty packageNameProperty() {
        return packageName;
    }

    public void setPackageName(String s) {
        packageName.setValue(s);
    }

    

    @XmlTransient
    public ObservableList<MethodComponent> getMethodsList() {
        return methodsList;
    }

    @XmlTransient
    public ObservableList<SimpleComponent> getFieldsList() {
        return fieldsList;
    }

    @Override
    public String getType() {
        return CLASS_TYPE;
    }

    @Override
    public String toString() {
        return className.getValue();
    }

    @Override
    public String addComponent(Component c) throws IllegalComponent {
        if (c.getType() == MethodComponent.METHOD_TYPE) {
            super.addComponent(c);
            getMethodsList().add((MethodComponent) c); //TODO add children method Listener
            return c.getUUID();
        }

        super.addComponent(c);
        getFieldsList().add((SimpleComponent) c); //TODO add children method Listener
        return c.getUUID();

//        throw new IllegalComponent();
        //TODO add field Component
    }

    

    private void componentChanged() {
        childStaments.clear();
        for (SimpleComponent c : fieldsList) {
            childStaments.add(c.getStatement());
        }
        for (MethodComponent c : methodsList) {
            childStaments.add(c.getStatement());
        }
        childrenChanged();

    }

}
