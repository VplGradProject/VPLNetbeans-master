/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.ide.ui.components.CanvasInsertComponentEventHandler;
import com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler;
import static com.icraus.ide.ui.components.DraggableCanvasComponentEventHandler.NODE_MOVE_FORMAT;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.shape.Line;

/**
 *
 * @author Shoka
 */
public class ComponentLine extends Line implements EventHandler<DragEvent> {

    private Component prevComponent = null;
    private ContextMenu menu = new ContextMenu();

    public ComponentLine(Node prev, Node next) {
//        prev.get
   
    
        startXProperty().bind(prev.layoutXProperty().add(prev.getBoundsInParent().getWidth() / 2.0));
        startYProperty().bind(prev.layoutYProperty().add(prev.getBoundsInParent().getHeight()));
        endXProperty().bind(next.layoutXProperty().add(next.getBoundsInParent().getWidth() / 2.0));
        endYProperty().bind(next.layoutYProperty());
        menu.getItems().add(new MenuItem("Paste"));
        setStrokeWidth(10);
        prevComponent = ((Selectable) prev).getParentComponent();
        setOnContextMenuRequested(e -> {
            menu.show(this, e.getScreenX(), e.getScreenY());
        });
        addEventHandler(DragEvent.ANY, this);
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

    private void dragOverHelper(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasContent(CanvasInsertComponentEventHandler.NODE_COPY_FORMAT)) {
            event.acceptTransferModes(TransferMode.COPY);
        }
    }

    private void dragDroppedHelper(DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean eventComplete = false;
        if (db.hasContent(CanvasInsertComponentEventHandler.NODE_COPY_FORMAT)) {
            Component c = CanvasInsertComponentEventHandler.copiedComponent;
            try {
                ComponentsModel.getInstance().insertComponentAfter(prevComponent, c);
            } catch (ComponentNotFoundException ex) {
                Logger.getLogger(ComponentLine.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalComponent ex) {
                Logger.getLogger(ComponentLine.class.getName()).log(Level.SEVERE, null, ex);
            }
            eventComplete = true;
        }
        event.setDropCompleted(eventComplete);
    }

}
