/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import static com.icraus.vpl.codegenerator.SimplePropertyStatement.ANY_PATTERN;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.NAME_END_VAR;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.NAME_START_VAR;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Shoka
 */
public class NameParserRule implements ParserRule {

    public NameParserRule() {
    }

    @Override
    public void applyParsing(SimplePropertyStatement statment, String text) throws IllegalStatementException {
        Pattern p = Pattern.compile(NAME_START_VAR + ANY_PATTERN + NAME_END_VAR);
        Matcher matcher = p.matcher(text);
        matcher.find();
        String name = matcher.group(1).trim();
        statment.setName(name);
    }

}
