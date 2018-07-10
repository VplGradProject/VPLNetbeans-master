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
import icraus.Components.UiManager;
import com.icraus.vpl.codegenerator.ErrorGenerateCodeException;
import com.icraus.vpl.codegenerator.Expression;
import com.icraus.vpl.codegenerator.IllegalStatementException;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.SimpleStatement;
import com.icraus.vpl.codegenerator.parsers.ErrorChoosingLanguage;
import com.icraus.vpl.codegenerator.parsers.TemplateCodeGenerator;
import static com.icraus.vpl.codegenerator.parsers.TemplateCodeGenerator.PYTHON_GRAMMAR_PROP_FILE;
import icraus.Components.ClassComponent;
import icraus.Components.Component;
import icraus.Components.ComponentFlags;
import icraus.Components.ComponentNotFoundException;
import icraus.Components.ComponentPluginFactories;
import icraus.Components.ComponentsModel;
import icraus.Components.IllegalComponent;
import icraus.Components.LanguageComponent;
import icraus.Components.LanguageModel;
import icraus.Components.MethodComponent;
import icraus.Components.Pageable;
import icraus.Components.ProjectComponent;
import icraus.Components.Selectable;
import icraus.Components.SimpleComponent;
import icraus.Components.SimpleComponentTabbed;
import icraus.Components.UiProperties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import javafx.util.StringConverter;
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
public class MainIDEController extends BorderPane /*implements Initializable */ {

    ComponentsModel model = ComponentsModel.getInstance();
    private static MainIDEController instance = new MainIDEController();
    private TemplateCodeGenerator jc = TemplateCodeGenerator.getInstance();

    @FXML
    private ComboBox<LanguageComponent> languageComboBox;
    @FXML
    private VBox libraryVBox;
    @FXML
    TreeView<Component> projectTree;

    @FXML
    private BorderPane mainPane;
    @FXML
    private Pane root;
    @FXML
    private Button bt2;
    @FXML
    private Button generateCodeButton;

    public static MainIDEController getInstance() {
        return instance;
    }
    private JAXBContext jaxbContext;
    private LanguageModel languageModel;
    private TemplateCodeGenerator templateGenerator;

    private MainIDEController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainIDE.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        projectTree.setCellFactory(new Callback<TreeView<Component>, TreeCell<Component>>() {
            @Override
            public TreeCell<Component> call(TreeView<Component> param) {
                TextFieldTreeCell<Component> cell = new TextFieldTreeCell<Component>(new StringConverter<Component>() {

                    @Override
                    public String toString(Component object) {
                        return object.toString();
                    }

                    @Override
                    public Component fromString(String string) {
                        //TODO add Return from String
                        return null;
                    }
                });
                cell.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            //FIXME fix this can't get right foucs
                            if (cell.getIndex() != 0) {
                                Component comp = cell.getItem();
                                Component parent = comp.getParent();
                                if ((parent.getFlags() & ComponentFlags.PAGEABLE_FLAG) > 0) {
                                    Tab p = ((Pageable) parent).getTab();
                                    p.getTabPane().getSelectionModel().select(p);
                                }
                                comp.getUiDelegate().requestFocus();

                            }
                        }
                    });

                });
                return cell;
            }
        });
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
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        }

        templateGenerator = TemplateCodeGenerator.getInstance();
        languageModel = LanguageModel.getInstance();
        projectTree.rootProperty().bind(model.treeItemsProperty());
        mainPane.centerProperty().setValue(UiManager.getInstance().getMainTabPane());
        languageComboBox.getItems().addAll(languageModel.getLanguageList());
        languageComboBox.getSelectionModel().selectedItemProperty().addListener(e -> {
            try {
                languageModel.LanguageChanged(languageComboBox.getSelectionModel().getSelectedItem());
            } catch (ErrorChoosingLanguage ex) {
                new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.CLOSE).showAndWait();
                Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    @FXML
    private void createLink() {

    }

    @FXML
    public void moveUp() {
        String str = model.getCurrentComponent();
        try {
            Component c = model.getComponentByUuid(str);
            c.moveUp();
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void moveDown() {
        String str = model.getCurrentComponent();
        try {
            Component c = model.getComponentByUuid(str);
            c.moveDown();
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void load() {
        ComponentClipBoard.getInstance().load();
    }

    @FXML
    public void saveAs() {
        ComponentClipBoard.getInstance().saveProject(null, "");
    }

    @FXML
    public void initialize() {
        ComponentListUi comps = new ComponentListUi();
        libraryVBox.getChildren().add(comps);
        sceneProperty().addListener(c -> {
            getScene().focusOwnerProperty().addListener((Observable e) -> {
                Node f = getScene().getFocusOwner();
                if (f instanceof Selectable) {
                    Selectable currentItem = (Selectable) f;
                    model.setCurrentComponent(currentItem.getParentComponentUuid());
                }

            });
        });
//        model.currentComponentProperty().addListener(e -> {
//
//        });

    }

    @FXML
    public void createNewProject() throws IllegalComponent, ComponentNotFoundException {
        new TextInputDialog().showAndWait().ifPresent(e -> {
            model.addProject(e);
        });
    }

    @FXML
    public void calculateTree() {
        model.calculateRoot();
        projectTree.setRoot(model.treeItemsProperty().get());
    }

    @FXML
    public void closeApp() {
        Platform.exit();
    }

    @FXML
    public void removeComponent() {
        try {
            model.removeComponetByUuid(model.getCurrentComponent());
        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        }

//        scene.get
    }

    @FXML
    public void generateCode() {
        String s = "";
        DirectoryChooser fc = new DirectoryChooser();
        File d = new File("C:\\Users\\Shoka\\Desktop\\New_folder");

        try {
            for (ProjectComponent cs : model.toList()) {
                for (Component c : cs.childernProperty()) {
                    ClassComponent cc = (ClassComponent) c;
                    String txt = cc.getStatement().toText();
                    templateGenerator.generateClass(d.getAbsolutePath(), cc.classNameProperty().get(), txt);
                    s += txt;
                }
            }
        } catch (ErrorGenerateCodeException ex) {
            Logger.getLogger(MainIDEController.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        String ss = templateGenerator.generateCode(s);
        System.out.println(ss);
    }

    //REMOVETHIS
    @FXML
    public void testAdd() {
        String uuid = model.addProject("new Project");
        try {
            String ccuuid = model.addComponent(uuid, new ClassComponent("Add", "BB"));
            String m = ccuuid;
            ccuuid = model.addMethodByUuid(ccuuid, new MethodComponent("method", "void", "public"));
            model.addMethodByUuid(m, new MethodComponent("run", "void", "public"));
            String d = ccuuid;
            String ifccuuid = model.addComponent(ccuuid, ComponentPluginFactories.createIfStatement());
            ccuuid = model.addComponent(ccuuid, ComponentPluginFactories.createForComponent());
            ccuuid = model.addComponent(d, ComponentPluginFactories.createForComponent());
            ccuuid = model.addComponent(d, ComponentPluginFactories.createInputValueComponent());
            ccuuid = model.addComponent(d, ComponentPluginFactories.createCommentComponent());
            ccuuid = model.addComponent(d, ComponentPluginFactories.createWhileComponent());
            ComponentClipBoard.getInstance().copy(model.getComponentByUuid(ccuuid));

        } catch (ComponentNotFoundException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalComponent ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStatementException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(MainIDEController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
