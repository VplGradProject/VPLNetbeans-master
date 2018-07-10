/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ide;

import com.icraus.vpl.codegenerator.ClassCodeBlockHead;
import com.icraus.vpl.codegenerator.CodeBlock;
import com.icraus.vpl.codegenerator.CodeBlockBody;
import com.icraus.vpl.codegenerator.CodeBlockImportsImpl;
import com.icraus.vpl.codegenerator.ConditionExpression;
import com.icraus.vpl.codegenerator.DeclareExpression;
import com.icraus.vpl.codegenerator.ErrorGenerateCodeException;
import com.icraus.vpl.codegenerator.Expression;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.SimpleStatement;
import icraus.Components.ClassComponent;
import icraus.Components.Component;
import icraus.Components.ComponentNotFoundException;
import icraus.Components.ComponentsModel;
import icraus.Components.IllegalComponent;
import icraus.Components.MethodComponent;
import icraus.Components.ProjectComponent;
import icraus.Components.SimpleComponent;
import icraus.Components.SimpleComponentTabbed;
import icraus.Components.UiProperties;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Shoka
 */
@XmlRootElement
class ComponentSaveWrapper {

    @XmlTransient
    private static final long serialVersionUID = 1L;

    private ArrayList<Component> componentList;

    public ComponentSaveWrapper() {
        super();
        this.componentList = new ArrayList<>();
    }

    public ComponentSaveWrapper(Collection<? extends Component> cl) {
        this.componentList = new ArrayList<>(cl);
        ;
    }

    @XmlElementWrapper(name = "wrapper")
    public ArrayList<Component> getComponentList() {
        return componentList;
    }

    public void setComponentList(ArrayList<Component> componentList) {
        this.componentList = componentList;
    }

}

public class ComponentClipBoard {

    private Component copiedComponent;
    private List<Component> childrenTree;
    private ComponentsModel componentModel = ComponentsModel.getInstance();
    private JAXBContext jaxbContext;

    private static ComponentClipBoard instance = new ComponentClipBoard();

    public static ComponentClipBoard getInstance() {
        return instance;
    }

    private ComponentClipBoard() {
        try {
            jaxbContext = JAXBContext.newInstance(ProjectComponent.class, Component.class,
                    UiProperties.class,
                    ClassComponent.class, SimpleComponent.class,
                    SimpleComponentTabbed.class, MethodComponent.class,
                    CodeBlockImportsImpl.class, ClassCodeBlockHead.class,
                    CodeBlockBody.class, Expression.class, CodeBlock.class,
                    DeclareExpression.class, CodeBlockImportsImpl.class, ConditionExpression.class,
                    SimpleStatement.class,
                    ComponentSaveWrapper.class, SimplePropertyStatement.class
            );
        } catch (JAXBException ex) {
            Logger.getLogger(ComponentClipBoard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() {
        try {

            Unmarshaller unmarshler = jaxbContext.createUnmarshaller();
            ComponentSaveWrapper proj = (ComponentSaveWrapper) unmarshler.unmarshal(new FileInputStream("C:\\Users\\Shoka\\Desktop\\New_folder\\1.xml"));
            System.out.println(proj.getComponentList());
            ArrayList<Component> arr = proj.getComponentList();
            for (int i = 0; i < arr.size(); ++i) {
                Component c = arr.get(i);
                if (c.getType() == ProjectComponent.PROJECT_TPYE) {
                    componentModel.addProject((ProjectComponent) c);
                } else {
                    try {
                        componentModel.addComponent(c.getParent().getUUID(), c);
                    } catch (ComponentNotFoundException ex) {
                        Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalComponent ex) {
                        Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveProject(ProjectComponent c, String path) {
        try {
            //        DirectoryChooser fc = new DirectoryChooser();
//        fc.setTitle("Save As");
//        File d = fc.showDialog(null);
            ObservableList<ProjectComponent> lst = componentModel.toList();
            ProjectComponent get = lst.get(0);//d.getPath() + "\\" + get.projectNameProperty().get() + ".xml"

            Marshaller marsh = jaxbContext.createMarshaller();
            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ComponentSaveWrapper Llst = new ComponentSaveWrapper(componentModel.getComponentTreeByUuid(get.getUUID()));
            marsh.marshal(Llst, System.out);

            marsh.marshal(Llst, (new FileOutputStream("C:\\Users\\Shoka\\Desktop\\New_folder\\1.xml")));

        } catch (JAXBException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);

//        } catch (ComponentNotFoundException ex) {
//            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Component getCopiedComponent() {
        return copiedComponent;
    }

    public void setCopiedComponent(Component copiedComponent) {
        this.copiedComponent = copiedComponent;
    }

    public void copy(Component comp) throws CloneNotSupportedException {
//        Component copied = comp.clone();
//        childrenTree = componentModel.getComponentTree(copied);
//        System.out.println(copied.getChildern());
//       copied.getStatement().getChildren().stream().map(e->{
//            try {
//                return e.toText();
//            } catch (ErrorGenerateCodeException ex) {
//                Logger.getLogger(ComponentClipBoard.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return "";
//        }).forEach(System.out::println);
////        childrenTree.remove(copied);
//        Queue<Component> q = new LinkedList<>();
//        q.add(copied);
//        while (!q.isEmpty()) {
//            Component tmp = q.poll();
//            q.addAll(tmp.childernProperty());
//            tmp.childernProperty().clear();
//        }
        setCopiedComponent(comp);
//        try {
//
//            Marshaller marsh = jaxbContext.createMarshaller();
//            marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//            ComponentSaveWrapper Llst = new ComponentSaveWrapper(componentModel.getComponentTreeByUuid(comp.getUUID()));
//            marsh.marshal(Llst, System.out);
//
//            marsh.marshal(Llst, (new FileOutputStream("C:\\Users\\Shoka\\Desktop\\New_folder\\temp.xml")));
//
//        } catch (JAXBException ex) {
//            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//
////        } catch (ComponentNotFoundException ex) {
////            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ComponentNotFoundException ex) {
//            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public Component paste(Component parent) throws ComponentNotFoundException, IllegalComponent, CloneNotSupportedException {
        if (getCopiedComponent() == null) {
            throw new ComponentNotFoundException("Nothing to paste here");
        }

//        Component comp = getCopiedComponent();
//        comp.setParent(parent);
//        Component copied = comp.clone();
//        childrenTree = componentModel.getComponentTree(comp);
//        System.out.println(copied.getChildern());
//        copied.getStatement().getChildren().stream().map(e -> {
//            try {
//                return e.toText();
//            } catch (ErrorGenerateCodeException ex) {
//                Logger.getLogger(ComponentClipBoard.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return "";
//        }).forEach(System.out::println);
//        childrenTree.remove(copied);
        Component copied = getCopiedComponent();

        List<Component> componentTree = componentModel.getComponentTree(copied);
        Map<Component, List<Component>> mapped = componentTree.stream().collect(Collectors.groupingBy(e -> {
            return e.getParent();
        }));
        Component root = null;
        for (Component c : mapped.keySet()) {
            Component clone = c.clone();
            if (c.equals(copied)) {
                clone.setParent(parent);
                root = clone;
            }

            List<Component> clonedList = mapped.get(c).stream().map(e -> {
                try {
                    Component s = e.clone();
                    s.setParent(clone);
                    return s;
                } catch (CloneNotSupportedException ex) {
                    Logger.getLogger(ComponentClipBoard.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }).collect(Collectors.toList());
            clone.childernProperty().addAll(clonedList);
//            childrenTree.add(clone);
//            childrenTree.addAll(collect);
        }
//        childrenTree = childrenTree.stream().distinct().collect(Collectors.toList());
        List<Component> lst = componentModel.getComponentTree(root);
        childrenTree.clear();
        childrenTree.addAll(lst);
//        Queue<Component> q = new LinkedList<>();
//        q.add(comp);
//        Component copied = comp.clone();
//        copied.setParent(parent);
//        childrenTree.add(copied);
//        while (!q.isEmpty()) {
//            Component tmp = q.poll();
//            List<Component> childrenCopied = tmp.childernProperty().stream().map((e) -> {
//                Component cp = null;
//                try {
//                    cp = e.clone();
//
//                    cp.setParent(copied);
//                } catch (CloneNotSupportedException ex) {
//                    Logger.getLogger(ComponentClipBoard.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                return cp;
//
//            }).collect(Collectors.toList());
//            childrenTree.addAll(childrenCopied);
//            q.addAll(tmp.childernProperty());
//        }
//        childrenTree.get(0).setParent(parent);
//        componentModel.addComponent(parent.getUUID(), comp);
        for (Component tmp : childrenTree) {
            if (tmp != null && tmp.getParent() != null) {
                componentModel.addComponent(tmp.getParent().getUUID(), tmp);
            }
        }
        return root;
//        try {
//
//            Unmarshaller unmarshler = jaxbContext.createUnmarshaller();
//            ComponentSaveWrapper proj = (ComponentSaveWrapper) unmarshler.unmarshal(new FileInputStream("C:\\Users\\Shoka\\Desktop\\New_folder\\temp.xml"));
//            System.out.println(proj.getComponentList());
//            ArrayList<Component> arr = proj.getComponentList();
//            Component get = arr.get(0);
//            componentModel.addComponent(parent.getUUID(), get);
//            for (int i = 1; i < arr.size(); ++i) {
//                Component c = arr.get(i);
//                if (c.getType() == ProjectComponent.PROJECT_TPYE) {
//                    componentModel.addProject((ProjectComponent) c);
//                } else {
//                    try {
//                        componentModel.addComponent(c.getParent().getUUID(), c);
//                    } catch (ComponentNotFoundException ex) {
//                        Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (IllegalComponent ex) {
//                        Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            }
//        } catch (JAXBException ex) {
//            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    public void cut(Component c) {
        //TODO add functionality
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
