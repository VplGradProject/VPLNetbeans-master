/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.IllegalStatementException;
import icraus.Components.util.ClassDialogs;
import static icraus.Components.UiManager.GUI_QUALIFIER;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import test.ComponentMapContextMenu;

/**
 *
 * @author Shoka
 */
public class ClassContentPane extends VBox implements Selectable, DraggableComponent {

    @FXML
    private Label classNameLabel;
    @FXML
    private VBox headVBox;
    @FXML
    private VBox methodsVBox;
    @FXML
    private VBox fieldsVBox;
    private ClassComponent parentComponent;
    private ContextMenu menu;
    @FXML
    private Label packageNameLabel;

    public ClassContentPane() {
        this(null);
    }

    public ClassContentPane(ClassComponent _parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClassContentPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        if (_parent != null) {
            initalize(_parent);
        }

        createContextMenu();
        createMouseEvent();
        String css = getClass().getResource("styleclass.css").toExternalForm();
        getStylesheets().add(css);

    }

    protected void drawMethods() {

        methodsVBox.getChildren().clear();
        ObservableList<MethodComponent> lst = parentComponent.getMethodsList();
        for (MethodComponent mc : lst) {
//            methodsVBox.getChildren().add(mc.getUiDelegate().getValue());
            methodsVBox.getChildren().add(mc.getUiDelegate());

        }
    }

    private void createContextMenu() {
        menu = new ComponentMapContextMenu(parentComponent);
        MenuItem addMethodItem = new MenuItem("Add Method");
        MenuItem addFieldItem = new MenuItem("Add Field");
        addMethodItem.setOnAction(e -> {
            Optional<MethodComponent> res = ClassDialogs.getAddMethodDialog().showAndWait();
            res.ifPresent(val -> {
                try {
                    ComponentsModel.getInstance().addMethodByUuid(getParentComponentUuid(), val);
                } catch (IllegalComponent ex) {
                    Logger.getLogger(ClassContentPane.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ComponentNotFoundException ex) {
                    Logger.getLogger(ClassContentPane.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        });
        addFieldItem.setOnAction(e -> {
            try {
                Component c = new DeclareFieldComponent();
                ComponentsModel.getInstance().addComponent(getParentComponentUuid(), c);
            } catch (ComponentNotFoundException ex) {
                Logger.getLogger(ClassContentPane.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalComponent ex) {
                Logger.getLogger(ClassContentPane.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalStatementException ex) {
                Logger.getLogger(ClassContentPane.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        menu.getItems().add(addFieldItem);
        menu.getItems().add(addMethodItem);
        this.setOnContextMenuRequested(e -> {
            menu.show(this, Side.LEFT, e.getX(), e.getY());
        });
    }

    private void createMouseEvent() {
//        this.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
//            if (e.getClickCount() == 2) {
//            }
//        });
    }

//    @FXML
    private void initialize() {
        parentComponent.getMethodsList().addListener((Observable e) -> {
            drawMethods();
        });

        parentComponent.getFieldsList().addListener((Observable e) -> {
            drawFields();
        });
        classNameLabel.textProperty().bindBidirectional(parentComponent.classNameProperty());
        packageNameLabel.textProperty().bindBidirectional(parentComponent.packageNameProperty());

    }

    @Override
    public String getParentComponentUuid() {
        return parentComponent.getUUID();
    }

    @Override
    public void initalize(Component parent) {
        setParentComponent((ClassComponent) parent);
    }

    public ClassComponent getParentComponent() {
        return parentComponent;
    }

    public void setParentComponent(ClassComponent parentComponent) {
        this.parentComponent = parentComponent;
        setId(GUI_QUALIFIER + parentComponent.getUUID());
        initialize();
    }

    @Override
    public ContextMenu getMenu() {
        return menu; //To change body of generated methods, choose Tools | Templates.
    }

    protected void drawFields() {
        fieldsVBox.getChildren().clear();
        ObservableList<SimpleComponent> lst = parentComponent.getFieldsList();
        for (SimpleComponent mc : lst) {
//            methodsVBox.getChildren().add(mc.getUiDelegate().getValue());
            fieldsVBox.getChildren().add(mc.getUiDelegate());
        }
    }

}
