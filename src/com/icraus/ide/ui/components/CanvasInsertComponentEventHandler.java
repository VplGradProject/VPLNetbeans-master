/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.ide.ui.components;

import static com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler.MOVE_IID;
import static com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler.NODE_MOVE_FORMAT;
import icraus.Components.Component;
import icraus.Components.ComponentPlugin;
import icraus.Components.IllegalComponentInstantiation;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 *
 * @author Shoka
 */
public class CanvasInsertComponentEventHandler implements EventHandler<MouseEvent> {

    public static Component copiedComponent;
    public static final String COPY_IID = "MOVE_IID";
    public static final DataFormat NODE_COPY_FORMAT = new DataFormat("COPY_NODE_IID");
    private ComponentPlugin plugin;

    public CanvasInsertComponentEventHandler(ComponentPlugin c) {
        plugin = c;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            dragDetectedHelper(event);
        }
    }

    private void dragDetectedHelper(MouseEvent event) {
        Node src = (Node) event.getSource();
        Dragboard db = src.startDragAndDrop(TransferMode.COPY);
        db.setDragView(src.snapshot(null, null));
        ClipboardContent cc = new ClipboardContent();
        try {
            copiedComponent = plugin.createComponent();
            cc.put(NODE_COPY_FORMAT, COPY_IID);
        } catch (IllegalComponentInstantiation ex) {
            Logger.getLogger(CanvasInsertComponentEventHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.setContent(cc);
    }

}
