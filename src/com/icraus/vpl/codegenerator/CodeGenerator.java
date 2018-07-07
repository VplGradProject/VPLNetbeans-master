/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.io.Serializable;

/**
 *
 * @author Shoka
 */
@FunctionalInterface
public interface CodeGenerator extends Serializable{
    
    public String toText() throws ErrorGenerateCodeException;

}
