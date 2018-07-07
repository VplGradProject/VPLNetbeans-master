/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Shoka
 */
@XmlTransient
public class LineStatement extends Statement{
    private Expression exp = null;

    public LineStatement() {
    }
    @XmlElementRef
    public Expression getExp() {
        return exp;
    }

    public void setExp(Expression exp) {
        this.exp = exp;
    }

    public LineStatement(Expression e){
        setExp(e);
    }
    
    
    @Override
    public String toText() throws ErrorGenerateCodeException{
        String str = null;
        try {
            str = getExp().getExpressionStr();
            
        } catch (NullPointerException e) {
            throw new ErrorGenerateCodeException();
        }
        return (str.trim() + GrammerConstants.OP_END_LINE);    
    }
    
}
