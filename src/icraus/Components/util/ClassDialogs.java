/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components.util;

import icraus.Components.MethodComponent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

/**
 *
 * @author Shoka
 */
public class ClassDialogs<T> extends Dialog<T> {
//    private AddMethodUi uiContent;
    private ClassDialogs() {
        super();
        //getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        //getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        getDialogPane().setStyle("-fx-background-color: #666666");    
        
        
    }
    public static Dialog<MethodComponent> getAddMethodDialog(){
        Dialog<MethodComponent> d = new ClassDialogs<>();
        AddMethodUi ui = new AddMethodUi(d);
        return d;
    }
    public static Dialog getAddFieldDialog(){
        Dialog d = new ClassDialogs<>();
//        AddFieldUi ui = new AddFieldUi(d);
        return d;
    }
    
}
