/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator.parsers;

/**
 *
 * @author Shoka
 */
public class ErrorChoosingLanguage extends Exception {

    public ErrorChoosingLanguage() {
        super("No Such file");
    }

    public ErrorChoosingLanguage(String no_Such_File) {
        super(no_Such_File);
    }
    
}
