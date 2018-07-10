/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import static com.icraus.vpl.codegenerator.SimplePropertyStatement.ANY_PATTERN;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.PROPERTIES_END_VAR;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.PROPERTIES_START_VAR;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.__ANY__VAR;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Shoka
 */
public class PropertiesParserRule implements ParserRule{

    public PropertiesParserRule() {
    }
    
    @Override
    public void applyParsing(SimplePropertyStatement statment, String text) throws IllegalStatementException{
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
                statment.getPropertyMap().put(str[0], str[1].trim());
                statment.getTypeMap().put(str[0], __ANY__VAR);
                statment.getPatternMap().put(str[0], __ANY__VAR);

            } else if (str.length == 3) {
                statment.getPropertyMap().put(str[0], str[1].trim());
                statment.getTypeMap().put(str[0], str[2].trim());
                statment.getPatternMap().put(str[0], __ANY__VAR);
            } else if (str.length == 4) {
                statment.getPropertyMap().put(str[0], str[1].trim());
                statment.getTypeMap().put(str[0], str[2].trim());
                statment.getPatternMap().put(str[0], str[3].trim());

            }
        }
    }
}
