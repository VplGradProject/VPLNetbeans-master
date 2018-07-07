/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SimpleStatement extends Statement {

    public SimpleStatement() {
        super("");
    }

    public SimpleStatement(String st) {
        super(st);
    }

    @Override
    public String toText() throws ErrorGenerateCodeException {
        return getStatementString();
    }
    
}
