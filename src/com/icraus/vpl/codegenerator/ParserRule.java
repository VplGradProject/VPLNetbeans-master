/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

/**
 *
 * @author Shoka
 */
public interface ParserRule {

    public void applyParsing(SimplePropertyStatement statment, String text) throws IllegalStatementException;
}
