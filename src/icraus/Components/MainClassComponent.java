/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.IllegalStatementException;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainClassComponent extends ClassComponent {

    public MainClassComponent() {
        super();
        try {
            statementRef = SimplePropertyStatement.fromFile("MAINCLASS_TEMPLATE.template");
            setStatement(statementRef);
            propertyMapProperty().putAll(statementRef.getPropertyMap());
            childStaments = statementRef.getChildren();
        } catch (IllegalStatementException ex) {
            Logger.getLogger(MainClassComponent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
