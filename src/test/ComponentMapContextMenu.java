/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import icraus.Components.Component;
import icraus.Components.SimpleComponent;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 *
 * @author Shoka
 */
public class ComponentMapContextMenu extends ContextMenu {

    private Map<String, String> contextMap;

    public ComponentMapContextMenu() {
    }

    public ComponentMapContextMenu(Component c) {
        MenuItem i = new MenuItem("edit");
        i.setOnAction(e -> {
            Stage stg = new Stage();
            stg.setResizable(false);
            PropertyDialog d = new PropertyDialog(c);
            Scene s = new Scene(d);
            stg.setScene(s);
            d.okButton.setOnAction((k) -> {
                if (d.changeResult()) {
                    stg.close();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Error Validation", ButtonType.OK).show();
                }
            });
            d.cancelButton.setOnAction(k -> {
                stg.close();
            });
            stg.showAndWait();
        });
        getItems().add(i);
    }

}
