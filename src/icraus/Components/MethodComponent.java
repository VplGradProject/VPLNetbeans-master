/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import test.LineLabel;
import com.icraus.vpl.codegenerator.IllegalStatementException;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javax.xml.bind.annotation.XmlRootElement;
import test.Item;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class MethodComponent extends SimpleComponentTabbed implements Pageable {

    public static final String METHOD_TYPE = "METHOD";
    private StringProperty methodName;
    public MethodComponent() throws IllegalStatementException {
        super(SimplePropertyStatement.fromFile("METHOD_TEMPLATE.template"), null, METHOD_TYPE);
        setUiProperties(UiProperties.createUiProperties(120, 80, "", ""));
        setUiDelegate(new LineLabel(this));
        propertyMapProperty().addListener((Observable e) ->{
            methodChanged();
        });
        methodName = new SimpleStringProperty();
        methodName.addListener((e) -> {
            methodChanged();
        });
    }

    public MethodComponent(String _name, String _returnType, String _access) throws IllegalStatementException {
        this();
        propertyMapProperty().put("%%Method Name%%", _name);
        propertyMapProperty().put("%%return type%%", _returnType);
        propertyMapProperty().put("%%access type%%", _access);
        setComponentString(_name);
        methodName.bindBidirectional(componentStringProperty());
    }
    @Override
    public int getFlags() {
        return super.getFlags() | ComponentFlags.CALLABLE_FLAG | ComponentFlags.PAGEABLE_FLAG; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getType() {
        return METHOD_TYPE;
    }

    @Override
    public String toString() {
        return getComponentString();
    }


    private void methodChanged() {
        String name = propertyMapProperty().get("%%Method Name%%");
        methodName.setValue(name);
        String ret = propertyMapProperty().get("%%return type%%");
        String access = propertyMapProperty().get("%%access type%%");
        String params = propertyMapProperty().get("%%parameters%%[]");
        LineLabel l = (LineLabel) getUiDelegate();
        l.setText(name, ret, params, access);
    }

    
}
