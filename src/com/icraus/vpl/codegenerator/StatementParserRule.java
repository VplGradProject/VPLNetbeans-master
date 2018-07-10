/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import static com.icraus.vpl.codegenerator.SimplePropertyStatement.ANY_PATTERN;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.STATEMENT_END_VAR;
import static com.icraus.vpl.codegenerator.SimplePropertyStatement.STATEMENT_START_VAR;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Shoka
 */
public class StatementParserRule implements ParserRule {

    public StatementParserRule() {
    }

    @Override
    public void applyParsing(SimplePropertyStatement statment, String text) throws IllegalStatementException {
        Pattern p = null;//Pattern.compile(PROPERTIES_START_VAR + ANY_PATTERN + PROPERTIES_END_VAR);
        Matcher matcher = null;//p.matcher(text);
        p = Pattern.compile(STATEMENT_START_VAR + ANY_PATTERN + STATEMENT_END_VAR);
        matcher = p.matcher(text);
        matcher.find();
        String stmnt = matcher.group(1).trim();
        String[] split = stmnt.split("\\|\\|LANG\\|\\|");
        for (String s : split) {
            p = Pattern.compile("@([\\w,\\s]+)@" + ANY_PATTERN + "@\\1@");
            matcher = p.matcher(s.trim());
            if (matcher.find()) {
                String stat = matcher.group(2).trim();
                String[] langs = matcher.group(1).split(",");
                for (String lang : langs) {
                    statment.getLanguageMap().put(lang.trim(), stat);
                }
            }
        }
        statment.setStatemetTemplate(stmnt);
    }

}
