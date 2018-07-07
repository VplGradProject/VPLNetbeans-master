/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import icraus.Components.Component;
import icraus.Components.DraggableComponent;
import icraus.Components.MethodComponent;
import icraus.Components.Pageable;
import icraus.Components.Selectable;
import icraus.Components.SimpleComponent;
import icraus.Components.UiManager;
import icraus.Components.UiProperties;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tab;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Shoka
 */
public class LineLabel extends Hyperlink implements Selectable, DraggableComponent {

    private SimpleComponent parent;

    public LineLabel(SimpleComponent _parent) {
        super();
        initalize(_parent);

        setUiParameters(_parent.getUiProperties());
        _parent.uiPropertiesProperty().addListener(e -> {
            setUiParameters(_parent.getUiProperties());
        });
        addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            if (e.getClickCount() == 2) {
                UiManager.getInstance().setCurrentTabByUuid(parent.getUUID());
            }
        });
        setWrapText(true);

        setFont(Font.font("System", FontWeight.BOLD, 13));
        setId(UiManager.GUI_QUALIFIER + this.parent.getUUID());
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
    }

    public LineLabel(String name, String returnType, String accessType, SimpleComponent d) {
        this(d);
        setText(name, returnType, "", accessType);

    }

    public LineLabel(String name, String returnType, String accessType, MethodComponent d) {
        this(d);
        setText(name, returnType, "", accessType);

    }

    public void setText(String name, String returnType, String params, String accessType) {
        String acc = "";
        switch (accessType) {
            case "public":
                acc = "+";
                break;
            case "private":
                acc = "-";
                break;
            case "protected":
                acc = "#";
                break;

        }
        super.setText(acc + " " + name + " (" + params + "): " + returnType);

    }

    public void setTextField(String name, String returnType, String value, String accessType) {
        String acc = "";
        switch (accessType) {
            case "public":
                acc = "+";
                break;
            case "private":
                acc = "-";
                break;
            case "protected":
                acc = "#";
                break;

        }
        super.setText(acc + " " + returnType + " " + name + " = " + value);

    }

    @Override
    public String getParentComponentUuid() {
        return parent.getUUID();
    }

    @Override
    public Component getParentComponent() {
        return parent;
    }

    @Override
    public void initalize(Component parent) {
        this.parent = (SimpleComponent) parent;
    }

    public void setParameters(double width, double height, String css, String cssId) {
        setMinWidth(width);
        setMinHeight(height);
        String sheet = getClass().getResource(css).toExternalForm();
        getStylesheets().add(sheet);
        getStyleClass().add(cssId);

    }

    private void setUiParameters(UiProperties uiProperties) {
        setText(((SimpleComponent) getParentComponent()).getComponentString());
        setParameters(uiProperties.getWidth(), uiProperties.getHeight(), uiProperties.getCss(), uiProperties.getCssId());
    }

}
