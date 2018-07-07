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
public class ErrorGenerateCodeException extends Exception {
    
    public ErrorGenerateCodeException(String s){
        super(s);
    }
    public ErrorGenerateCodeException() {
        this("Error Code Generation");
    }
    
}
