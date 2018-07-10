/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.IllegalStatementException;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.Item;

public class ComponentPluginFactories extends SimpleComponentPlugin {

    private ComponentPluginFactories() {
    }

    public static ComponentPlugin createIfComponentPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createIfStatement();
                },
                (c) -> {
                });
    }

    public static ComponentPlugin createMainComponentPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createMainStatement();
                },
                (c) -> {
                });
    }

    public static Component createSimpleComponent(SimplePropertyStatement.FILE_TYPE type, String template, double width, double height, String css, String cssId) {
        SimplePropertyStatement s = null;
        try {
            s = SimplePropertyStatement.fromFile(type, template);
        } catch (IllegalStatementException ex) {
            Logger.getLogger(ComponentPluginFactories.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleComponent comp = new SimpleComponent(s, null, s.getName());
        UiProperties props = UiProperties.createUiProperties(width, height, css, cssId);
        comp.setUiProperties(props);
        Item node = new Item(comp);
        comp.setUiDelegate(node);
        return comp;
    }
    
    public static Component createSimpleComponent(String template, double width, double height, String css, String cssId) {
        SimplePropertyStatement s = null;
        try {
            s = SimplePropertyStatement.fromFile(template);
        } catch (IllegalStatementException ex) {
            Logger.getLogger(ComponentPluginFactories.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleComponent comp = new SimpleComponent(s, null, s.getName());
        UiProperties props = UiProperties.createUiProperties(width, height, css, cssId);
        comp.setUiProperties(props);
        Item node = new Item(comp);
        comp.setUiDelegate(node);
        return comp;
    }

    public static Component createSimpleTabbedComponent(String template, double width, double height, String css, String cssId) {
        SimplePropertyStatement s = null;
        try {
            s = SimplePropertyStatement.fromFile(template);
        } catch (IllegalStatementException ex) {
            Logger.getLogger(ComponentPluginFactories.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleComponent comp = new SimpleComponentTabbed(s, null, s.getName());
        UiProperties props = UiProperties.createUiProperties(width, height, css, cssId);
        comp.setUiProperties(props);
        Item node = new Item(comp);
        comp.setUiDelegate(node);
        return comp;
    }

    public static Component createSimpleTabbedComponent(SimplePropertyStatement.FILE_TYPE type, String template, double width, double height, String css, String cssId) {
        SimplePropertyStatement s = null;
        try {
            s = SimplePropertyStatement.fromFile(type, template);
        } catch (IllegalStatementException ex) {
            Logger.getLogger(ComponentPluginFactories.class.getName()).log(Level.SEVERE, null, ex);
        }
        SimpleComponent comp = new SimpleComponentTabbed(s, null, s.getName());
        UiProperties props = UiProperties.createUiProperties(width, height, css, cssId);
        comp.setUiProperties(props);
        Item node = new Item(comp);
        comp.setUiDelegate(node);
        return comp;
    }

    public static Component createIfStatement() {
        return createSimpleTabbedComponent("IF_TEMPLATE.template", 80.0, 80.0, "ifStyle.css", "ifStatement");
    }

    public static Component createMainStatement() {
        return createSimpleTabbedComponent("MAINCLASS_TEMPLATE.template", 80.0, 80.0, "ifStyle.css", "ifStatement");
    }

    public static Component createForComponent() {

        return createSimpleTabbedComponent("FOR_TEMPLATE.template", 120, 40, "forStyle.css", "forLoop");
    }

    public static ComponentPlugin createForComponentPlugin(String name, String section) {
        return new SimpleComponentPlugin(name,
                section,
                null,
                () -> {
                    return createForComponent();
                },
                (c) -> {
                });
    }

    public static Component createInputValueComponent() {
        return createSimpleComponent("INPUT_VALUE.template", 120, 40, "inputStyle.css", "inputValue");

    }

    public static ComponentPlugin createInputValue(String name, String section) {
        return new SimpleComponentPlugin(name, section, null, () -> {
            return createInputValueComponent();
        });
    }

    public static Component createCommentComponent() {
        return createSimpleComponent("COMMENT.template", 120, 40, "commentStyle.css", "comment");

    }

    public static ComponentPlugin createComment(String name, String section) {
        return new SimpleComponentPlugin(name, section, null, () -> {
            return createCommentComponent();
        });
    }

    public static Component createCallMethodComponent() {
        return createSimpleComponent("CALL_METHOD.template", 120, 40, "callStyle.css", "callMethod");

    }

    public static ComponentPlugin createCallMethod(String name, String section) {
        return new SimpleComponentPlugin(name, section, null, () -> {
            return createCallMethodComponent();
        });
    }

    public static Component createOutputComponent() {
        return createSimpleComponent("OUTPUT.template", 120, 40, "outputStyle.css", "outputExpression");
    }

    public static ComponentPlugin createOutput(String name, String section) {
        return new SimpleComponentPlugin(name, section, null, () -> {
            return createOutputComponent();
        });
    }

    public static Component createWhileComponent() {
        return createSimpleTabbedComponent("WHILE_TEMPLATE.template", 120, 40, "whileStyle.css", "whileLoop");

    }

    public static ComponentPlugin createWhileComponentPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createWhileComponent();
                },
                (c) -> {
                });
    }

    public static Component createDeclareComponent() {
        return createSimpleComponent("DECLARE_VAR.template", 120, 40, "declareStyle.css", "declareVariable");

    }

    public static ComponentPlugin createDeclareVarPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createDeclareComponent();
                },
                (c) -> {
                });
    }

    public static Component createAssignComponent() {
        return createSimpleComponent("ASSIGN_VALUE.template", 120, 40, "", "");

    }

    public static ComponentPlugin createAssignVarPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createAssignComponent();
                },
                (c) -> {
                });
    }
    
    public static Component createDATABASECOLUMNComponent() {
        return createSimpleComponent(SimplePropertyStatement.FILE_TYPE.FILE, "F:\\Important\\Code\\Graduation_Project\\VPLNetbeans-master\\plugins\\COLUMN_TEMP.template", 120, 40, "", "");

    }
    public static Component createDBCREATETABLEComponent() {
        return createSimpleTabbedComponent(SimplePropertyStatement.FILE_TYPE.FILE, "F:\\Important\\Code\\Graduation_Project\\VPLNetbeans-master\\plugins\\" + "CREATETABLE_TEMPLATE" + ".template", 120, 40, "", "");

    }
    public static Component createDBDELETEComponent() {
        return createSimpleComponent(SimplePropertyStatement.FILE_TYPE.FILE, "F:\\Important\\Code\\Graduation_Project\\VPLNetbeans-master\\plugins\\"+"DELETE_TEMPLATE" + ".template", 120, 40, "", "");

    }
    public static Component createDBINSERTComponent() {
        return createSimpleComponent(SimplePropertyStatement.FILE_TYPE.FILE, "F:\\Important\\Code\\Graduation_Project\\VPLNetbeans-master\\plugins\\"+"INSERTDB_TEMPLATE" + ".template", 120, 40, "", "");

    }
    public static Component createDBSelectComponent() {
        return createSimpleComponent(SimplePropertyStatement.FILE_TYPE.FILE, "F:\\Important\\Code\\Graduation_Project\\VPLNetbeans-master\\plugins\\"+"SELECT_TEMPLATE" + ".template", 120, 40, "", "");

    }
    public static Component createDBUPDATEDB_TEMPLATEComponent() {
        return createSimpleComponent(SimplePropertyStatement.FILE_TYPE.FILE, "F:\\Important\\Code\\Graduation_Project\\VPLNetbeans-master\\plugins\\"+"UPDATEDB_TEMPLATE" + ".template", 120, 40, "", "");

    }
    
    public static ComponentPlugin createDBColPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createDATABASECOLUMNComponent();
                },
                (c) -> {
                });
    }
    public static ComponentPlugin createDBSelectPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createDBSelectComponent();
                },
                (c) -> {
                });
    }
    public static ComponentPlugin createDBDeletePlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createDBDELETEComponent();
                },
                (c) -> {
                });
    }
    public static ComponentPlugin createDBCreatePlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createDBCREATETABLEComponent();
                },
                (c) -> {
                });
    }
    public static ComponentPlugin createDBInsertPlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createDBINSERTComponent();
                },
                (c) -> {
                });
    }
    public static ComponentPlugin createDBUpdatePlugin(String componentName, String sectionName) {

        return new SimpleComponentPlugin(componentName,
                sectionName,
                null,
                () -> {
                    return createDBUPDATEDB_TEMPLATEComponent();
                },
                (c) -> {
                });
    }
}
