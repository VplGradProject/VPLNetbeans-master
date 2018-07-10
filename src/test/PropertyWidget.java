/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import icraus.Components.Component;
import icraus.Components.ComponentsModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import com.icraus.vpl.codegenerator.parsers.PropertiesParser.Formatter;
import static com.icraus.vpl.codegenerator.parsers.PropertiesParser.*;

/**
 *
 * @author Shoka
 */
public class PropertyWidget extends GridPane {

    private final ObservableMap<String, String> propertyMap;
    private Map<String, StringProperty> resultMap;
    private final Map<String, String> patternMap;
    private final Map<String, String> typeMap;

    public Map<String, StringProperty> getResultMap() {
        return resultMap;
    }

    public PropertyWidget(Component c) {
        this.resultMap = new HashMap<>();
        propertyMap = c.propertyMapProperty();
        SimplePropertyStatement statement = c.getStatement();
        patternMap = statement.getPatternMap();
        typeMap = statement.getTypeMap();
        setHgap(5);
        setVgap(5);
        drawProperties();
    }

    protected void drawProperties() {
        getChildren().clear();
        Label n = new Label("Name");
        Label v = new Label("Value");
        Label reg = new Label("Regex");
        add(n, 0, 0);
        add(v, 1, 0);
        add(reg, 2, 0);
        int i = 1;
        for (String s : propertyMap.keySet()) {
            String a = s.replaceAll("%%", "");
            Label name = new Label(a);
            String pat = patternMap.get(s);
            String val = propertyMap.get(s);
            val = formatLookAHeadInput(pat, val);
            Result value = addValueField(typeMap.get(s), val);
            resultMap.put(s, value.property);
            Label pattern = new Label(pat);
            add(name, 0, i);
            add(value.node, 1, i);
            add(pattern, 2, i);
            ++i;
        }

    }

    private Result addChoiseHelper(String type, String name) {
        Result r = new Result();
        ChoiceBox<Formatter> box = new ChoiceBox<>();
        box.getItems().addAll(parseChoiceOutputs(type, name));
        r.node = box;
        r.property = new SimpleStringProperty();
        box.getSelectionModel().selectedItemProperty().addListener(e -> {
            String s = box.getSelectionModel().getSelectedItem().value;
            r.property.setValue(s);
        });
        box.getSelectionModel().select(0);
        return r;
    }

    private Result addAutoCompleterHelper(String type, String name) {
        Result r = new Result();
        AutoCompleteTextField cf = new AutoCompleteTextField(name);
        List<String> stringLst = createAutoCompeleteList(type, name);
        cf.getEntries().addAll(stringLst);
        r.node = cf;
        r.property = cf.textProperty();
        return r;
    }

    private Result addTextFieldHelper(String type, String name) {
        Result r = new Result();
        TextField f = new TextField();
        r.node = f;
        r.property = f.textProperty();
        f.setText(name);
        return r;
    }

    protected Result addValueField(String type, String name) {

        if (type.contains(SimplePropertyStatement.__CHOICE__VAR)) {
            return addChoiseHelper(type, name);
        }
        if (type.contains(SimplePropertyStatement.__VAR__TYPE_CONSTRAINT)) {
            return addAutoCompleterHelper(type, name);
        }
        return addTextFieldHelper(type, name);
    }
}

class Result {

    Node node;
    StringProperty property;
}
