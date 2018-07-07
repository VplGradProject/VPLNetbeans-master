/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.Statement;
import com.sun.javafx.collections.ObservableMapWrapper;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javax.xml.bind.annotation.XmlRootElement;
import test.Item;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class SimpleComponent extends Component {

    private int userFlags = 0;
    private StringProperty componentString = new SimpleStringProperty("");
    private ObjectProperty<UiProperties> uiProperties = new SimpleObjectProperty<>(new UiProperties());
    private ObservableMap<String, String> propertyMap = new ObservableMapWrapper<>(new HashMap<>());
    
    public ObjectProperty<UiProperties> uiPropertiesProperty(){
        return uiProperties;
    }


    public String getComponentString() {
        return componentString.getValue();
    }
    public StringProperty componentStringProperty() {
        return componentString;
    }
    public void setComponentString(String componentString) {
        this.componentString.setValue(componentString);
    }
    public int getUserFlags(){
        return userFlags;
    }
    public void setUserFlags(int userFlags) {
        this.userFlags = userFlags;
    }

    public SimpleComponent() {
        super();
        setUiDelegate(new Item(this));
    }

    public SimpleComponent(SimplePropertyStatement s, Node delegate, String _type) {
        setStatement(s);
        setUiDelegate(delegate);
        setType(_type);
        setComponentString(s.getName());
        this.setPropertyMap(s.getPropertyMap());
        propertyMapProperty().addListener((Observable e) -> {
            ((SimplePropertyStatement) getStatement()).setPropertyMap(propertyMap);
        });
    }

    public SimpleComponent(Statement s, Node delegate, String _type) {
        super();
//        setStatement(s);
        setUiDelegate(delegate);
        setType(_type);
        setComponentString(_type);
    }

    public SimpleComponent(Statement s, Node delegate, String _type, String str, int flags) {
        this(s, delegate, _type, str);
        userFlags = flags;

    }

    public SimpleComponent(Statement s, Node delegate, String _type, String str) {
        super();
//        setStatement(s);
        setUiDelegate(delegate);
        setType(_type);
        componentString.setValue(str);

    }

    @Override
    public String addComponent(int index, Component c) throws IllegalComponent {
        if (!(this instanceof SimpleComponentTabbed)) {
            throw new IllegalComponent("Can't add Here");
        } else {
            return super.addComponent(index, c);
        }
    }

    @Override
    public int getFlags() {
        return super.getFlags() | ComponentFlags.DRAGGABLE_FLAG | userFlags; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return getComponentString();
    }

    public UiProperties getUiProperties() {
        return uiProperties.get();
    }

    public void setUiProperties(UiProperties properties) {
        this.uiProperties.set(properties);
    }
    

}
