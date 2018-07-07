/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide;

import com.icraus.ide.ui.components.CanvasInsertComponentEventHandler;
import com.sun.javafx.collections.ObservableMapWrapper;
import icraus.Components.ComponentNotFoundException;
import icraus.Components.ComponentPlugin;
import icraus.Components.ComponentsModel;
import icraus.Components.IllegalComponent;
import icraus.Components.IllegalComponentInstantiation;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.Observable;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 *
 * @author Shoka
 */
public class ComponentListUi extends VBox implements MapChangeListener<Object, Object> {

    private ComponentsManager manager = ComponentsManager.getInstance();
    private ObservableMap<String, TitledPane> sections = new ObservableMapWrapper<>(new HashMap());
    @FXML
    private Accordion rootAccordionPane;
    @FXML
    private Label lbl;

    public Accordion getRootAccordionPane() {
        return rootAccordionPane;
    }

    public Map<String, TitledPane> getSections() {
        return sections;
    }

//    public void setSections(Map<String, TitledPane> sections) {
//        this.sections = sections;
//    }
    public void setSections(ObservableMap<String, TitledPane> sections) {
        this.sections = sections;
    }

    public ComponentListUi() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ComponentUi.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        sections.addListener((Observable e) -> {
            loadPanes();
        });
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

    }

    protected void addDraggableEventHandler(Node lbl, ComponentPlugin c) {
        lbl.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            ComponentsModel instance = ComponentsModel.getInstance();
            try {
                String uid = instance.addComponent(instance.getCurrentComponent(), c.createComponent());
            } catch (IllegalComponentInstantiation | ComponentNotFoundException | IllegalComponent ex) {
                ex.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error Adding Component", ButtonType.OK).showAndWait();
            }
        }
        );
        lbl.addEventFilter(MouseEvent.DRAG_DETECTED, new CanvasInsertComponentEventHandler(c));
    }

    protected void loadPanes() {
        rootAccordionPane.getPanes().clear();
        for (String key : sections.keySet()) {
            TitledPane p = sections.get(key);
            p.setText(key);
            rootAccordionPane.getPanes().add(p);
        }
    }

    protected void addNodeToSection(Node n, String section) {
        TitledPane sec = getSection(section);
        Pane content = (Pane) sec.getContent();
        content.getChildren().add(n);
    }

    private TitledPane getSection(String section) {
        boolean cond = sections.containsKey(section);
        TitledPane cu = null;
        if (cond) {
            cu = sections.get(section);
        } else {
            cu = new TitledPane();
            VBox box = new VBox();
            box.setSpacing(15);
            box.setAlignment(Pos.CENTER);
            cu.setContent(box);
            sections.put(section, cu);
        }
        return cu;
    }

    protected void loadComponenets() {
        ObservableList<ComponentPlugin> lst = manager.getPluginList();
        for (ComponentPlugin c : lst) {
            
            Button b = new Button(c.getComponentName(), c.getIcon());
            b.setFont(Font.font("System", FontWeight.BOLD, 20));
            applyComponentStyle(b);
            
            Node n = b;
            addNodeToSection(n, c.getSectionName());
            addDraggableEventHandler(n, c);
        }
    }

    @FXML
    private void initialize() {
        loadComponenets();
//        loadPanes();
    }

    @Override
    public void onChanged(Change<? extends Object, ? extends Object> change) {
        throw new UnsupportedOperationException("Not supported yet."); //TODO add observer for component changes
    }
    
    private void applyComponentStyle(Button b){
        String txt = b.getText();
        switch (txt){
            case "Input":
                b.setStyle("-fx-background-color:#6666ff ; -fx-shape: \"M20 0 L100 0 L80 50 L0 50 Z\"");
                break;
            case "Output":
                b.setStyle("-fx-background-color:#99ffcc ; -fx-shape: \"M20 0 L100 0 L80 50 L0 50 Z\";");
                break;
            case "Comment":
                b.setStyle("-fx-background-color:#ffffff ; -fx-shape: \"M0 0 L100 0 L100 50 L0 50 Z\"; -fx-border-style: dashed;");
                break;
            case "Call Method":
                b.setText(" Call Method ");
                b.setStyle("-fx-background-color:#ff9999 ; -fx-shape: \"M0 0 L10 0 L10 50 L11 50 L11 0 L90 0 L90 50 L91 50 L91 0 L100 0 L100 50 L0 50 Z\";");
                b.setFont(Font.font("System", FontWeight.BOLD, 18));
                break;
            case "If":
                b.setFont(Font.font("System", FontWeight.BOLD, 22));
                b.setStyle("-fx-background-color: #ff0000; -fx-shape: \"M0 50 L50 0 L100 50 L50 100 Z\"");
                break;
            case "For":
                b.setText("  For  ");
                b.setStyle("-fx-background-color:#00cc00 ; -fx-shape: \"M25 0 L75 0 L100 25 L75 50 L25 50 L0 25 Z\";");
                break;
            case "While":
                b.setStyle("-fx-background-color:#00cccc ; -fx-shape: \"M25 0 L75 0 L100 25 L75 50 L25 50 L0 25 Z\";");
                break;
            case "Class":
                b.setStyle("-fx-background-color:#2d92b8; -fx-text-fill:#ffff00; -fx-border-color:#06428e; -fx-border-width: 3;");
                break;
            default:
                break;
        }
    }

}
