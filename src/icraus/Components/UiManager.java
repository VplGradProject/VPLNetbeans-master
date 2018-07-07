/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author Shoka
 */
public class UiManager {

    public static final String GUI_QUALIFIER = "GUI";
    private static UiManager instance = new UiManager();
    private ObservableList<Tab> methodsTabs;
    private TabPane mainTabPane;

    public static UiManager getInstance() {
        return instance;
    }
    private final ComponentsModel model;
//    private final Tab mainTab;

    private UiManager() {
        this.mainTabPane = new TabPane();
        this.methodsTabs = mainTabPane.getTabs();
        mainTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        methodsTabs.clear();
        model = ComponentsModel.getInstance();
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((Observable e) -> {
            try {
                Node c = mainTabPane.getSelectionModel().getSelectedItem().getContent();
                ScrollPane p = (ScrollPane) c;
                Selectable n = (Selectable) p;
                model.setCurrentComponent(n.getParentComponentUuid());
            }catch(Exception ex){
                ex.printStackTrace();
            }
        });
//        model.getModel().addListener((Observable e) -> {
//            ObservableList<Tab> tabs = getMainTabPane().getTabs();
//            tabs.clear();
//            for(ProjectComponent c : model.toList()) {
//                tabs.add(c.getTab());
//            }
//            
//        });
    }

    public Node selectElementByUuId(String uuid) {
        return getMainTabPane().lookup("#" + GUI_QUALIFIER + uuid);
    }

    public ObservableList<Tab> getMethodsTabs() {
        return methodsTabs;
    }

    public void removeTab(Tab t){
        mainTabPane.getTabs().remove(t);
        
    }
    public void addTab(Tab t) {
        methodsTabs.add(t);
    }

    public Tab getTabByUuid(String uuid) {
        try {
            Pageable comp = (Pageable) ComponentsModel.getInstance().getComponentByUuid(uuid);
            Tab methodTab = comp.getTab();
            return methodTab;
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(UiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public void setCurrentTabByUuid(String uuid){
        getMainTabPane().getSelectionModel().select(getTabByUuid(uuid));
    }

    public Tab getCurrentTab() {
        return mainTabPane.getSelectionModel().getSelectedItem();
    }

    public TabPane getMainTabPane() {
        return mainTabPane;
    }

    public void setMainTabPane(TabPane mainTabPane) {
        this.mainTabPane = mainTabPane;
    }

}
