/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DeclareExpression extends Expression {
    public static final String VAR_VAR_TYPE = "$$VARTYPE";
    public static final String VAR_VAR_NAME = "$$VARNAME";
    public static final String VAR_VAR_VALUE = "$$VARVALUE";
    public static final String DECLARE_EXP_TEMPALTE = "$$VARTYPE $$VARNAME $$VARVALUE"; 
    
    private String imports = "";
    public DeclareExpression() {
    }
    public DeclareExpression(String s) {
        setExpressionStr(s);
    }
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int hashCode() {
        return super.hashCode(); //To change body of generated methods, choose Tools | Templates.
    }
    public String getImports(){
        return imports;
    }
}
