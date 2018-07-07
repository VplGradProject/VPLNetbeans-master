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
public class IllegalStatementException extends Exception {

    public IllegalStatementException() {
        super("Error parsing File");
    }
    public IllegalStatementException(String s){
        super(s);
    }
    
}
