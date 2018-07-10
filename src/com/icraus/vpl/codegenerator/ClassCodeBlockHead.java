/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

//TODO Add DECORATOR For access Modifiers, storage qualifier
@XmlRootElement
public class ClassCodeBlockHead implements CodeBlockHead {

    public static final String VAR_CLASS_PARENT = "$$PARENT_CLASS";
    public static final String VAR_INTERFACES_NAME = "$$INTERFACE_CLASS";
    public static final String VAR_CLASS_NAME = "$$CLASS_NAME";
    public static final String VAR_PACKAGE_NAME = "$$PACKAGE_NAME";
    public static final String VAR_ACCESS_TYPE = "$$ACCESS_TYPE";
    public static final String CLASS_TEMPLATE
            = GrammerConstants.STAT_PACKAGE
            + " "
            + VAR_PACKAGE_NAME
            + GrammerConstants.OP_END_LINE
            + GrammerConstants.END_LINE
            + VAR_ACCESS_TYPE
            + " "
            + GrammerConstants.STAT_CLASS
            + " "
            + VAR_CLASS_NAME
            + " "
            + GrammerConstants.STAT_EXTEND_WORD
            + " "
            + VAR_CLASS_PARENT
            + " "
            + GrammerConstants.STAT_IMPLEMENTS_WORD
            + " "
            + VAR_INTERFACES_NAME;

    private String className = "";
    private String parentClass = "";
    private List<String> interfaceNames = new ArrayList<>();
    private String statementTemplate;
    private String packageName = "";
    private String accessType = "";

    public ClassCodeBlockHead() {
        this(CLASS_TEMPLATE);
    }

    protected ClassCodeBlockHead(String template) {
        this.statementTemplate = CLASS_TEMPLATE;

    }

    public String getParentClass() {
        return parentClass.trim();
    }

    public void setParentClass(String parentClass) {
        this.parentClass = parentClass;
    }

    @Override
    public String statementTemplateString() {
        return statementTemplate.trim();
    }

    @Override
    public String toText() throws ErrorGenerateCodeException {
        String res = statementTemplateString();
        res = res.replace(VAR_PACKAGE_NAME, getPackageName());
        res = res.replace(VAR_ACCESS_TYPE, getAccessType());
        res = res.replace(VAR_CLASS_NAME, getClassName());
        res = res.replace(VAR_CLASS_PARENT, getParentClass());
        if (getParentClass().trim().isEmpty()) {
            res = res.replace(GrammerConstants.STAT_EXTEND_WORD, "");
        }
        String interfaces = String.join(", ", getInterfaceNames()).trim();
        res = res.replace(VAR_INTERFACES_NAME, interfaces);
        if (interfaces.trim().isEmpty()) {
            res = res.replace(GrammerConstants.STAT_IMPLEMENTS_WORD, "");
        }
        return res.trim();
    }

    public String getClassName() {
        return className.trim();
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public List<String> getInterfaceNames() {
        return interfaceNames;
    }

    public void setInterfaceNames(List<String> interfaceNames) {
        this.interfaceNames = interfaceNames;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    public String getStatementTemplate() {
        return statementTemplate;
    }

    public void setStatementTemplate(String statementTemplate) {
        this.statementTemplate = statementTemplate;
    }

}
