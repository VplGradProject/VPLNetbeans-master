/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import static com.icraus.vpl.codegenerator.SimplePropertyStatement.ANY_PATTERN;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.COMPONENT_TYPE_END;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.COMPONENT_TYPE_START;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Shoka
 */
public class TypeParserRule implements ParserRule {

    public TypeParserRule() {
    }

    @Override
    public void applyParsing(SimplePropertyStatement statment, String text) throws IllegalStatementException {
        Pattern p = Pattern.compile(COMPONENT_TYPE_START + ANY_PATTERN + COMPONENT_TYPE_END);
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            statment.setType(matcher.group(1).trim());
        }
    }

}
