/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class SimplePropertyStatement extends Statement {
    
    public static final String __VAR__TYPE_CONSTRAINT = "__VAR__TYPE__";
    public static final String __VAR__TYPE = "__VAR__TYPE__";
    public static final String ANY_PATTERN = "([\\w:\\s-!$%^&*()_+|~=`{}\\[\\]:\";'<>?,.\\/\\\\]*)";
    public static final String __ANY__VAR = "__ANY__";
    public static final String __CHOICE__VAR = "__CHOICE__";
    public static final String NAME_END_VAR = "%%NAME_END%%";
    public static final String NAME_START_VAR = "%%NAME_START%%";
    public static final String STATEMENT_END_VAR = "%%STATEMENT_END%%";
    public static final String STATEMENT_START_VAR = "%%STATEMENT_START%%";
    public static final String PROPERTIES_END_VAR = "%%PROPERTIES_END%%";
    public static final String PROPERTIES_START_VAR = "%%PROPERTIES_START%%";

    public enum FILE_TYPE {
        FILE, RESOURCE
    }
    private String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    private String statemetTemplate = "";
    private Map<String, String> propertyMap = new HashMap<>();
    private Map<String, String> patternMap = new HashMap<>();
    private Map<String, String> typeMap = new HashMap<>();

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
        Pattern p = Pattern.compile(PROPERTIES_START_VAR + ANY_PATTERN + PROPERTIES_END_VAR);
        Matcher matcher = p.matcher(text);
        matcher.find();
        String props = matcher.group(1).trim();
        String[] split = props.split("\\n");
        for (String prop : split) {
            String[] str = prop.split(":");
            if (str.length < 2) {
                throw new IllegalStatementException();
            } else if (str.length == 2) {
                propertyMap.put(str[0], str[1].trim());
                typeMap.put(str[0], __ANY__VAR);
                patternMap.put(str[0], __ANY__VAR);

            } else if (str.length == 3) {
                propertyMap.put(str[0], str[1].trim());
                typeMap.put(str[0], str[2].trim());
                patternMap.put(str[0], __ANY__VAR);
            } else if (str.length == 4) {
                propertyMap.put(str[0], str[1].trim());
                typeMap.put(str[0], str[2].trim());
                patternMap.put(str[0], str[3].trim());

            }
        }
        p = Pattern.compile(STATEMENT_START_VAR + ANY_PATTERN + STATEMENT_END_VAR);
        matcher = p.matcher(text);
        matcher.find();
        String stmnt = matcher.group(1).trim();
        setStatemetTemplate(stmnt);
        p = Pattern.compile(NAME_START_VAR + ANY_PATTERN + NAME_END_VAR);
        matcher = p.matcher(text);
        matcher.find();
        String name = matcher.group(1).trim();
        setName(name);
        try {
            f.close();
        } catch (IOException ex) {
            Logger.getLogger(SimplePropertyStatement.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public String toText() throws ErrorGenerateCodeException {

        String result = getStatemetTemplate();
        for (String s : propertyMap.keySet()) {
            result = result.replace(s, propertyMap.get(s));
        }
        String childs = "";
        for (Statement s : getChildren()) {
            childs += s.toText() + "\n";
        }
        childs = childs.trim();
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
