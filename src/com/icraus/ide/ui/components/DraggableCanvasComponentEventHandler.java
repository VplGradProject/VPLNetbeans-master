package com.icraus.ide.ui.components;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

/**
 * For Moving Items inside Canvas
 *
 * @author Shoka
 * @version 1.0
 * @created 17-?????-2018 07:20:27 ?
 */
public class DraggableCanvasComponentEventHandler implements EventHandler<MouseEvent> {

    public static final String MOVE_IID = "MOVE_IID";
    public static final DataFormat NODE_MOVE_FORMAT = new DataFormat("MOVE_NODE_IID");
    
    public DraggableCanvasComponentEventHandler() {
        
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getEventType() == MouseEvent.DRAG_DETECTED) {
            dragDetectedHelper(event);  
        }
    }

    private void dragDetectedHelper(MouseEvent event) {
        Node src = (Node) event.getSource();        
        Dragboard db = src.startDragAndDrop(TransferMode.MOVE);
        db.setDragView(src.snapshot(null, null));
        ClipboardContent cc = new ClipboardContent();
        cc.put(NODE_MOVE_FORMAT, MOVE_IID);
        db.setContent(cc);
    }
}
