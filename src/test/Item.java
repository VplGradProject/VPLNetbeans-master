package test;

import icraus.Components.Component;
import icraus.Components.DraggableComponent;
import icraus.Components.Pageable;
import icraus.Components.Selectable;
import icraus.Components.SimpleComponent;
import icraus.Components.UiManager;
import icraus.Components.UiProperties;
import java.util.UUID;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Item extends Button implements DraggableComponent, Selectable {
//Add Validation For each component

    private SimpleComponent parentComp;

    @Override
    public SimpleComponent getParentComponent() {
        return parentComp;
    }

    public Item() {
    }

    public Item(SimpleComponent _parent) {
        initalize(_parent);

        setUiParameters(_parent.getUiProperties());
        _parent.uiPropertiesProperty().addListener(e -> {
            setUiParameters(_parent.getUiProperties());
        });
        setFont(Font.font("System", FontWeight.BOLD, 20));
        setId(UiManager.GUI_QUALIFIER + this.parentComp.getUUID());
        setMinWidth(120);
        setMinHeight(40);
        ContextMenu m = new ComponentMapContextMenu(getParentComponent());
        setContextMenu(m);
        addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            try {
                if ((_parent instanceof Pageable) && e.getClickCount() == 2) {
                    Tab t = UiManager.getInstance().getTabByUuid(getParentComponentUuid());
                    t.getTabPane().getSelectionModel().select(t);
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        });
        layoutXProperty().addListener(e -> {
            parentComp.getUiProperties().setX(getLayoutX());
        });
        layoutYProperty().addListener(e -> {
            parentComp.getUiProperties().setY(getLayoutY());
        });

    }

    @Override
    public String getParentComponentUuid() {
        return getParentComponent().getUUID();
    }

    @Override
    public void initalize(Component parent) {
        this.parentComp = (SimpleComponent) parent;
    }

    public void setParameters(double x, double y, double width, double height, String css, String cssId) {
        setMinWidth(width);
        setMinHeight(height);
        String sheet = getClass().getResource(css).toExternalForm();
        getStylesheets().add(sheet);
        getStyleClass().add(cssId);

        setLayoutX(x);
        setLayoutY(y);

    }

    private void setUiParameters(UiProperties uiProperties) {
        setText(getParentComponent().getStatement().getName());
        setParameters(uiProperties.getX(), uiProperties.getY(), uiProperties.getWidth(), uiProperties.getHeight(), uiProperties.getCss(), uiProperties.getCssId());
    }
}
