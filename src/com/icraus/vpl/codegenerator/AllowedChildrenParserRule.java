/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import static com.icraus.vpl.codegenerator.SimplePropertyStatement.ALLOWED_CHILDREN_END_VAR;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.ALLOWED_CHILDREN_START_VAR;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.ANY_PATTERN;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Shoka
 */
public class AllowedChildrenParserRule implements ParserRule {

    public AllowedChildrenParserRule() {
    }

    @Override
    public void applyParsing(SimplePropertyStatement statment, String text) throws IllegalStatementException {
        Pattern p = Pattern.compile(ALLOWED_CHILDREN_START_VAR + ANY_PATTERN + ALLOWED_CHILDREN_END_VAR);
        Matcher matcher = p.matcher(text);
        if (matcher.find()) {
            String[] allowedchild = matcher.group(1).trim().split(",");
            for (String s : allowedchild) {
                statment.getAllowedChildren().add(s.trim());
            }
        }
    }

}
