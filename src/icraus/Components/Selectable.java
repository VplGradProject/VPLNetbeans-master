/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shoka
 */
public interface Selectable {
    public void initalize(Component parent);
    public String getParentComponentUuid();
    public default Component getParentComponent() {
        try {
            return ComponentsModel.getInstance().getComponentByUuid(getParentComponentUuid());
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(Selectable.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
