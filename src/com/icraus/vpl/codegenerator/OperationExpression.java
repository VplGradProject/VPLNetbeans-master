/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import com.icraus.vpl.codegenerator.Expression;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class OperationExpression extends Expression{

    public OperationExpression() {
        this("");
    }
    
    public OperationExpression(String s){
        super.setExpressionStr(s);
    }
}
