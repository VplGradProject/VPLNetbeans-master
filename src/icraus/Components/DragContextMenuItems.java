/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.MenuItem;

/**
 *
 * @author Shoka
 */
public class DragContextMenuItems {

    public static List<MenuItem> createContextMenu(Component c){
        List<MenuItem> itms = new ArrayList<>();
        MenuItem removeItm = new MenuItem("remove");
        removeItm.setOnAction(e ->{
            try {
                ComponentsModel.getInstance().removeComponetByUuid(c.getUUID());
            } catch (ComponentNotFoundException ex) {
                Logger.getLogger(DragContextMenuItems.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        itms.add(removeItm);
        MenuItem moveUp = new MenuItem("move up");
        moveUp.setOnAction(e ->{
            c.moveUp();
        });
        itms.add(moveUp);
        MenuItem moveDown = new MenuItem("move down");
        moveDown.setOnAction(e ->{
            c.moveDown();
        });
        itms.add(moveDown);
        return itms;
    }
    
}
