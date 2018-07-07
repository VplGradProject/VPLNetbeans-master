/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.ide.ui.maincomponents;

import com.icraus.vpl.codegenerator.DeclareExpressionBuilder;
import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 *
 * @author Shoka
 */
public class AddFieldUi extends VBox implements ChangeListener<String>{
    private DeclareExpressionBuilder declareExpBuilder = new DeclareExpressionBuilder();

   
    @FXML
    Button okButton;
    @FXML
    Button cancelButton;
    
    @FXML
    TextField fieldName;
    @FXML
    TextField fieldType;
    @FXML
    TextField fieldValue;
    @FXML
    ComboBox<String> fieldAccessTypeComboBox;
    Dialog parentDialog;
    
    public AddFieldUi(Dialog parent) {
        parentDialog = parent;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddFieldUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
    public AddFieldUi(Dialog parent, DeclareExpressionBuilder builder){
        this(parent);
        declareExpBuilder = builder;
    }
    
    @FXML
    private void initialize() {
        parentDialog.getDialogPane().setContent(this);
        fieldAccessTypeComboBox.getItems().add("public");
        fieldAccessTypeComboBox.getItems().add("private");
        fieldAccessTypeComboBox.getItems().add("default");
        fieldAccessTypeComboBox.getItems().add("protected");
        fieldName.textProperty().addListener(this);
        fieldType.textProperty().addListener(this);
        fieldValue.textProperty().addListener(this);
        fieldAccessTypeComboBox.valueProperty().addListener(this);
        okButton.setOnAction(e-> {
            parentDialog.setResult(declareExpBuilder.buildExpression());
            parentDialog.close();
            
        });
        cancelButton.setOnAction(e-> {
            parentDialog.close();            
        });
    }

    private String stringHelper(String s){
        if (s == null || s.isEmpty()){
            return "";
        }
        return s;
    }
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        String type = stringHelper(fieldType.textProperty().get());
        String strName = stringHelper(fieldName.textProperty().get());
        String strVal = stringHelper(fieldValue.textProperty().get());
        declareExpBuilder.setVarType(type);
        declareExpBuilder.setVarValue(strName, strVal);    
    }
     public DeclareExpressionBuilder getDeclareExpBuilder() {
        return declareExpBuilder;
    }

    public void setDeclareExpBuilder(DeclareExpressionBuilder declareExpBuilder) {
        this.declareExpBuilder = declareExpBuilder;
    }
}
