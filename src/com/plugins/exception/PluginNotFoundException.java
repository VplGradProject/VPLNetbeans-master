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
public class PluginNotFoundException extends Exception {

    public PluginNotFoundException() {
        this("Plugin Not Found ");
    }
    public PluginNotFoundException(String message) {
        super(message);
    }
    
}
