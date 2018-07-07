/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import javafx.scene.Node;


/**
 *
 * @author Shoka
 */
public interface ComponentPlugin extends ComponentFactory, ComponentInializer, NodeFactory{
    public String getSectionName();
    public String getComponentName();
    public Node getIcon();
}
