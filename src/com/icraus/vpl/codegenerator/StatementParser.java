/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shoka
 */
public class StatementParser {

    private List<ParserRule> parserRuleLst = new ArrayList<>();
    private static StatementParser instance = new StatementParser();

    public static StatementParser getInstance() {
        return instance;
    }

    private StatementParser() {
        ParserRule statementRule = new StatementParserRule();
        ParserRule nameRule = new NameParserRule();
        ParserRule typeRule = new TypeParserRule();
        ParserRule allowedChildrenRule = new AllowedChildrenParserRule();
        registerRule(new PropertiesParserRule());
        registerRule(statementRule);
        registerRule(nameRule);
        registerRule(typeRule);
        registerRule(allowedChildrenRule);

    }

    public void registerRule(ParserRule rule) {
        parserRuleLst.add(rule);
    }

    public void applyParsing(SimplePropertyStatement aThis, String text) throws IllegalStatementException {
        for (ParserRule rule : parserRuleLst) {
            rule.applyParsing(aThis, text);
        }
    }

}
