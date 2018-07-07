/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.plugins.exception;

/**
 *
 * @author Mohamed Khaled(icraus)
 */
public class PluginErrorLoadingException extends Exception {

    public PluginErrorLoadingException() {
        super("Error Loading Plugin");
    }
    
}
