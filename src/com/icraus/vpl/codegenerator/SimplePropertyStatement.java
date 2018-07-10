/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import static com.icraus.vpl.codegenerator.GrammerConstants.PASS_WRD;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class SimplePropertyStatement extends Statement {
//    public static String currentLanguage = 

    public static final String __VAR__TYPE_CONSTRAINT = "__VAR__TYPE__";
    public static final String __VAR__TYPE = "__VAR__TYPE__";
    public static final String ANY_PATTERN = "([\\w:\\s-!$@%^&*()_#+|~=`{}\\[\\]:\";'<>?,.\\/\\\\]*)";
    public static final String __ANY__VAR = "__ANY__";
    public static final String __NONE__VAR = "__NONE__";
    public static final String __CHOICE__VAR = "__CHOICE__";
    public static final String NAME_END_VAR = "%%NAME_END%%";
    public static final String NAME_START_VAR = "%%NAME_START%%";
    public static final String STATEMENT_END_VAR = "%%STATEMENT_END%%";
    public static final String STATEMENT_START_VAR = "%%STATEMENT_START%%";
    public static final String PROPERTIES_END_VAR = "%%PROPERTIES_END%%";
    public static final String PROPERTIES_START_VAR = "%%PROPERTIES_START%%";
    public static final String COMPONENT_TYPE_END = "%%COMPONENT_TYPE_END%%";
    public static final String COMPONENT_TYPE_START = "%%COMPONENT_TYPE_START%%";

    public static final String ALLOWED_CHILDREN_END_VAR = "%%ALLOWED_CHILDREN_END%%";
    public static final String ALLOWED_CHILDREN_START_VAR = "%%ALLOWED_CHILDREN_START%%";
    public static String __OTHERS__VAR = "__OTHERS__VAR__";
    private static String generationLanguage = "JAVA";
    private static int i = 0;
    public static String getGenerationLanguage() {
        return generationLanguage;
    }

    public static void setGenerationLanguage(String aGenerationLanguage) {
        generationLanguage = aGenerationLanguage;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getAllowedChildren() {
        return allowedChildren;
    }

    public void setAllowedChildren(List<String> allowedChildren) {
        this.allowedChildren = allowedChildren;
    }

    public Map<String, String> getLanguageMap() {
        return languageMap;
    }

    public void setLanguageMap(Map<String, String> languageMap) {
        this.languageMap = languageMap;
    }

    public enum FILE_TYPE {
        FILE, RESOURCE
    }
    private String name = "";
    private String type = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
//    private String statemetTemplate = "";
    private String statemetTemplate = "";
    private Map<String, String> languageMap = new HashMap<>();
    private Map<String, String> propertyMap = new HashMap<>();
    private Map<String, String> patternMap = new HashMap<>();
    private Map<String, String> typeMap = new HashMap<>();
    private List<String> allowedChildren = new ArrayList<>();

    public Map<String, String> getPatternMap() {
        return patternMap;
    }

    public void setPatternMap(Map<String, String> patternMap) {
        this.patternMap = patternMap;
    }

    public Map<String, String> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, String> typeMap) {
        this.typeMap = typeMap;
    }
    private List<Statement> children = new ArrayList<>();
    public static String CHILDREN_TOKEN = "%%CHILDREN_TOKEN%%";

    public SimplePropertyStatement() {
    }

    public SimplePropertyStatement(SimplePropertyStatement rhs) {
        propertyMap = new HashMap<>(rhs.propertyMap);
        patternMap = new HashMap<>(rhs.patternMap);
        allowedChildren = new ArrayList<>(rhs.allowedChildren);
        typeMap = new HashMap<>(rhs.typeMap);
        name = rhs.name;
        type = rhs.type;
        children = rhs.children.stream().map(e -> {
            return new SimplePropertyStatement((SimplePropertyStatement) e);
        }).collect(Collectors.toList());
//        propertyMap = new HashMap<>(rhs.propertyMap);
    }

    public static SimplePropertyStatement fromFile(String resource) throws IllegalStatementException {
        return fromFile(FILE_TYPE.RESOURCE, resource);
    }

    public static SimplePropertyStatement fromFile(FILE_TYPE t, String path) throws IllegalStatementException {
        SimplePropertyStatement s = new SimplePropertyStatement();
        switch (t) {

            case FILE:
                s.fromRegularFileHelper(path);
                break;
            case RESOURCE:
                s.fromResouceHelper(path);
                break;
        }
        return s;
    }

    public SimplePropertyStatement(String template) {
        this();
        statemetTemplate = template;
    }

    private void fromRegularFileHelper(String path) throws IllegalStatementException {
        File f = new File(path);
        try {
            FileInputStream s = new FileInputStream(f);
            fromFileHelper(s);
        } catch (FileNotFoundException ex) {
            throw new IllegalStatementException("No Such File");
        }
    }

    private void fromResouceHelper(String name) throws IllegalStatementException {
        InputStream f = getClass().getResourceAsStream(name);
        fromFileHelper(f);
    }

    private void fromFileHelper(InputStream f) throws IllegalStatementException {
        Scanner scan = new Scanner(f);
        String text = "";
        scan.useDelimiter("");
        while (scan.hasNext()) {
            text += scan.next();
        }
        StatementParser parser = StatementParser.getInstance();
        parser.applyParsing(this, text);
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(SimplePropertyStatement.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private String getStatementTemplate(String lang) throws ErrorGenerateCodeException {
        if (languageMap.containsKey(__ANY__VAR) || languageMap.isEmpty()) {
            return getStatemetTemplate();
        }

        String template = languageMap.get(lang);
        if (template == null) {
            if (languageMap.containsKey(__OTHERS__VAR)) {
                String stm = languageMap.get(__OTHERS__VAR);
                setStatemetTemplate(stm);
                return stm;
            }
            setStatemetTemplate("/*UNSUPPORTED_BY_THIS_LANGUAGE" + lang + "*/");
            return template;
        }
        setStatemetTemplate(template);
        return template;

    }
    private String dublicate(String s, int i){
        for(int n = 0; n < i;++n){
            s += s;
        }
        return s;
    }
    @Override
    public String toText() throws ErrorGenerateCodeException {
        getStatementTemplate(getGenerationLanguage());
        String result = getStatemetTemplate();
        for (String s : propertyMap.keySet()) {
            result = result.replace(s, propertyMap.get(s));
        }
        ++i;
        String childs = "";
        if(getChildren().isEmpty()){
            childs = dublicate(" ", i) + GrammerConstants.PASS_WRD;
        }
        for (Statement s : getChildren()) {
            String[] stringArr = s.toText().split("\n");
            for(String d : stringArr){
                childs += dublicate(" ", i) + d + "\n"; 
            }
        }
        --i;
//        childs = childs.substring(1);
//        childs = childsss.substring(1, childs.length() - 1);
        result = result.replace(CHILDREN_TOKEN, childs);
        return result;
    }

    public String getStatemetTemplate() {
        return statemetTemplate;
    }

    public void setStatemetTemplate(String statemetTemplate) {
        this.statemetTemplate = statemetTemplate;
    }

    public List<Statement> getChildren() {
        return children;
    }

    public void setChildren(List<Statement> children) {
        this.children = children;
    }

    public Map<String, String> getPropertyMap() {
        return propertyMap;
    }

    public void setPropertyMap(Map<String, String> _propertyMap) {
        propertyMap.putAll(_propertyMap);
    }

}
