/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator.parsers;

import com.icraus.vpl.codegenerator.ErrorGenerateCodeException;
import com.icraus.vpl.codegenerator.Statement;

/**
 *
 * @author Shoka
 */
public abstract class StatemntWrapper extends Statement{

    private Statement wrapped;

    public StatemntWrapper(Statement s) {
        wrapped = s;
    }

    public Statement getWrapped() {
        return wrapped;
    }

    public void setWrapped(Statement wrapped) {
        this.wrapped = wrapped;
    }
    
}
