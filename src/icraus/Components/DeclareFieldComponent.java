/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.IllegalStatementException;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import javafx.beans.Observable;
import javafx.scene.Node;
import javax.xml.bind.annotation.XmlRootElement;
import test.LineLabel;

@XmlRootElement
public class DeclareFieldComponent extends SimpleComponent {

    public DeclareFieldComponent() throws IllegalStatementException {
        super(SimplePropertyStatement.fromFile("DECLARE_FIELD.template"), null, "VAR");
        setUiProperties(UiProperties.createUiProperties(80, 80, "", ""));
        setUiDelegate(new LineLabel(this));
        propertyMapProperty().addListener((Observable e) -> {
            propertyChanged();
        });
        setType("DECLARE_FIELD");
    }

    public DeclareFieldComponent(SimplePropertyStatement s, Node delegate, String _type) {
        super(s, delegate, _type);
    }

    private void propertyChanged() {
        String ret = propertyMapProperty().get("%%variable type%%");

        String name = propertyMapProperty().get("%%variable name%%");
        String value = propertyMapProperty().get("%%variable value%%");
        String access = propertyMapProperty().get("%%access type%%");
        LineLabel l = (LineLabel) getUiDelegate();
        l.setTextField(name, ret, value, access);
    }

}
