/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator.parsers;

import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import icraus.Components.Component;
import icraus.Components.ComponentsModel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Shoka
 */
public class PropertiesParser {

    public static String formatLookAHeadInput(String pattern, String name) {
        Pattern namePattern = Pattern.compile("(D+)" + "%([<>=]*)\\{" + SimplePropertyStatement.ANY_PATTERN + "\\}\\2+%");
        Matcher m = namePattern.matcher(pattern);
        String value = name;
        if (m.find()) {
            value = name.replace(m.group(3), "").trim();
        }
        return value;

    }

    public static String formatLookAHeadOutput(String name, String pattern) {
        Pattern namePattern = Pattern.compile("(D+)" + "%([<>=]*)\\{" + SimplePropertyStatement.ANY_PATTERN + "\\}\\2+%");
        Matcher m = namePattern.matcher(pattern);
        String value = name;
        if (m.find()) {
            switch (m.group(2)) {
                case "<":
                    value = m.group(3) + " " + name;
                    break;
                case ">":
                    value += " " + m.group(3);
                    break;
                case "=":
                    value = m.group(3) + " " + name + " " + m.group(3);
                    break;
            }
        }
        return value;

    }

    public static List<String> createAutoCompeleteList(String type, String name) {
        Matcher m = null;
        Pattern p = Pattern.compile("__VAR__TYPE__%%" + SimplePropertyStatement.ANY_PATTERN + "%%\\[" + SimplePropertyStatement.ANY_PATTERN + "\\]");
        m = p.matcher(type);
        m.find();
        String[] componentType = m.group(1).split(",");
        String[] variable = m.group(2).split(",");
        List<Component> lst = new ArrayList<>();
        for (String v : componentType) {
            lst.addAll(ComponentsModel.getInstance().getComponentsByType(v));
        }
        List<String> stringLst = new ArrayList<>();
        for (Component c : lst) {
            for (String v : variable) {
                stringLst.add(c.getPropertyMap().get(v));
            }
        }
        return stringLst;

    }

    public static List<Formatter> parseChoiceOutputs(String type) {
        Pattern p = Pattern.compile(SimplePropertyStatement.__CHOICE__VAR + "%%" + SimplePropertyStatement.ANY_PATTERN + "%%\\[" + SimplePropertyStatement.ANY_PATTERN + "\\]");
        Matcher m = null;
        m = p.matcher(type);
        m.find();
        String[] variables = m.group(2).split(",");
        String ptrn = SimplePropertyStatement.ANY_PATTERN + "%\\{" + SimplePropertyStatement.ANY_PATTERN + "\\}%";
        List<Formatter> lst = new ArrayList<>();
        for (String s : variables) {
            String str = s.trim();
            Formatter f = new Formatter(str, str);
            if (str.contains("%{") && str.contains("}%")) {
                p = Pattern.compile(ptrn);
                m = p.matcher(str);
                m.find();
                f.shown = m.group(1);
                f.value = m.group(2);
            }
            lst.add(f);
        }
        return lst;
    }

    public static class Formatter {

        public String shown;
        public String value;

        public Formatter() {
        }

        public Formatter(String shown) {
            this.shown = shown;
            this.value = shown;
        }

        public Formatter(String shown, String value) {
            this.shown = shown;
            this.value = value;
        }

        @Override
        public String toString() {
            return shown;
        }

    }
}
