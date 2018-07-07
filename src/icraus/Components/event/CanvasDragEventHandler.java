/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components.event;

import com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler;
import static com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler.NODE_MOVE_FORMAT;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;

/**
 *
 * @author Shoka
 */
public class CanvasDragEventHandler implements EventHandler<DragEvent> {


    public CanvasDragEventHandler() {
    }

    @Override
    public void handle(DragEvent event) {
        if (event.getEventType() == DragEvent.DRAG_OVER) {
            dragOverHelper(event);
        }
        if (event.getEventType() == DragEvent.DRAG_DROPPED) {
            dragDroppedHelper(event);
        }
    }

    private void addToCanvas(Node lbl, Pane target, double x, double y) {
        lbl.setLayoutX(x);
        lbl.setLayoutY(y);
        target.getChildren().add(lbl);
    }

    private void dragDroppedHelper(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean eventComplete = false;
        if (db.hasContent(DraggableCanvasComponentEventHandler.NODE_MOVE_FORMAT)) {
            Node src = (Node) event.getGestureSource();
            src.setLayoutX(event.getX());
            src.setLayoutY(event.getY());
            eventComplete = true;
        }
        event.setDropCompleted(eventComplete);
    }

    private void dragOverHelper(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasContent(NODE_MOVE_FORMAT)) {
            event.acceptTransferModes(TransferMode.MOVE);
        }

    }
}
