/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;

/**
 *
 * @author Shoka
 */
public interface DraggableComponent {
    public default ContextMenu getMenu(){
        return ((Control)this).getContextMenu();
    }
}
