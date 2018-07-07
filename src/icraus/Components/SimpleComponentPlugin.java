/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Node;

public class SimpleComponentPlugin implements ComponentPlugin {

    private static final String COMPONENT_CLASS_NAME_PROPERTY = "COMPONENT_CLASS_NAME";
    private static final String COMPONENT_NAME_PROPERTY = "COMPONENT_NAME";
    private static final String COMPONENT_SECTION_NAME_PROPERTY = "COMPONENT_SECTION_NAME";
    private static final String COMPONENT_NODE_PROPERTY = "COMPONENT_NODE";
    private String componentName;
    private String sectionName;
    private Node graphic;
    private Component component;
    private ComponentFactory componentFactory;
    private ComponentInializer initalizer;
    private NodeFactory nodeFactory;

    public static SimpleComponentPlugin fromFile(String fileName) throws IllegalComponentInstantiation{
        SimpleComponentPlugin plugin = new SimpleComponentPlugin();
        Properties prop = new Properties();
        try (FileInputStream s = new FileInputStream(fileName)) {
            prop.load(s);
            plugin.setComponentFactory( ()->{
                try {
                    return (Component)Class.forName(prop.getProperty(COMPONENT_CLASS_NAME_PROPERTY)).newInstance();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                throw new IllegalComponentInstantiation();
            });
            plugin.setComponentName(prop.getProperty(COMPONENT_NAME_PROPERTY));
            plugin.setSectionName(prop.getProperty(COMPONENT_SECTION_NAME_PROPERTY));
            plugin.setInitalizer((c)->{});
            plugin.setNodeFactory((Component c)->{
                try {
                    Selectable node =  (Selectable) Class.forName(prop.getProperty(COMPONENT_NODE_PROPERTY)).newInstance();
                    node.initalize(c);
                    return node;
                } catch (ClassNotFoundException |IllegalAccessException| InstantiationException e) {
                    e.printStackTrace();
                    throw new IllegalNodeInstantiation();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalComponentInstantiation("Component Not Found");
    }

    public NodeFactory getNodeFactory() {
        return nodeFactory;
    }

    public void setNodeFactory(NodeFactory nodeFactory) {
        this.nodeFactory = nodeFactory;
    }



    public SimpleComponentPlugin(String componentName, String sectionName, Component component, Node graphic, ComponentFactory factory, ComponentInializer initC) {
        this.componentName = componentName;
        this.sectionName = sectionName;
        this.graphic = graphic;
        this.component = component;
        this.componentFactory = factory;
        this.initalizer = initC;

    }

    public SimpleComponentPlugin() {
    }

    public SimpleComponentPlugin(String componentName, String sectionName, Node graphic, ComponentFactory factory, ComponentInializer initC) {
        this(componentName, sectionName, null, graphic, factory, initC);
    }

    public SimpleComponentPlugin(String componentName, String sectionName, Node graphic, ComponentFactory factory) {
        this(componentName, sectionName, graphic, factory, (c)->{});
    }

    public SimpleComponentPlugin(String componentName, String sectionName, Component component, Node graphic) {
        this(componentName, sectionName, component, graphic, null, null);
        setComponentFactory(() -> {
            try {
                Component instance = component.getClass().newInstance();
                return instance;
            } catch (InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(SimpleComponentPlugin.class.getName()).log(Level.SEVERE, null, ex);
                throw new IllegalComponentInstantiation("Can't create " + getComponentName());
            }
        });
        setInitalizer((c) -> {

        });
    }

    @Override

    public Component createComponent() throws IllegalComponentInstantiation {
        Component tmp = getComponentFactory().createComponent();
        initalize(tmp);
        return tmp;
    }

    @Override
    public String getSectionName() {
        return sectionName;
    }

    @Override
    public String getComponentName() {
        return componentName;
    }

    @Override
    public Node getIcon() {
        return graphic;
    }

    @Override
    public void initalize(Component n) {
        initalizer.initalize(n);
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public void setGraphic(Node graphic) {
        this.graphic = graphic;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public ComponentFactory getComponentFactory() {
        return componentFactory;
    }

    public void setComponentFactory(ComponentFactory componentFactory) {
        this.componentFactory = componentFactory;
    }

    public ComponentInializer getInitalizer() {
        return initalizer;
    }

    public void setInitalizer(ComponentInializer initalizer) {
        this.initalizer = initalizer;
    }

    public Component getComponent() {
        return component;
    }

    @Override
    public Selectable createNode(Component c) throws IllegalNodeInstantiation{
        return nodeFactory.createNode(c);
    }


}
