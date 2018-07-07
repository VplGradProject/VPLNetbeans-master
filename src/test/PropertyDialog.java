/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.Statement;
import icraus.Components.Component;
import icraus.Components.SimpleComponent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import static com.icraus.vpl.codegenerator.parsers.PropertiesParser.formatLookAHeadOutput;

/**
 *
 * @author Shoka
 */
public class PropertyDialog extends VBox {

    @FXML
    public Button okButton;
    @FXML
    public Button cancelButton;
    @FXML
    private HBox contentHBox;
    @FXML
    private HBox headHBox;
    @FXML
    private Label nameLabel;
    private final PropertyWidget wdgt;
    private Component component;

    public PropertyDialog(Component comp) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PropertyDialog.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.component = comp;
        wdgt = new PropertyWidget(comp);
        nameLabel.setText(comp.getType());
        contentHBox.getChildren().add(wdgt);
    }

    public boolean changeResult() {
        Map<String, StringProperty> res = wdgt.getResultMap();
        SimplePropertyStatement stmnt = (SimplePropertyStatement) component.getStatement();
        Map<String, String> patternMap = stmnt.getPatternMap();
        Map<String, String> tmpMap = new HashMap<>(stmnt.getPropertyMap());
        for (String c : component.getPropertyMap().keySet()) {
            String txt = res.get(c).get();
            String pattern = patternMap.get(c);
            if (pattern.startsWith("P") || pattern.contains(SimplePropertyStatement.__ANY__VAR)) {
                if (validateInput(pattern, txt)) {
                    tmpMap.put(c, txt);
                } else {
                    return false;
                }
            } else if (pattern.startsWith("D")) {
                String formatter = formatLookAHeadOutput(txt, pattern);
                tmpMap.put(c, formatter);
            }
        }

        component.propertyMapProperty().putAll(tmpMap);

        return true;
    }

    private boolean validateInput(String pattern, String text) {
        if (pattern.equals(SimplePropertyStatement.__ANY__VAR) || pattern.equals("")) {
            return true;
        } else {
            String pt = pattern.substring(1);
            return text.matches(pt);
        }

    }

}
