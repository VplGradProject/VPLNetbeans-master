/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;


public class DeclareExpressionBuilder extends ExpressionBuilder {
    public static final String VAR_VAR_TYPE = "$$VARTYPE";
    public static final String VAR_VAR_NAME = "$$VARNAME";
    public static final String VAR_VAR_VALUE = "$$VARVALUE";
    public static final String OP_ASSIGNMENT = "=";

    public static final String DECLARE_EXP_TEMPALTE = VAR_VAR_TYPE +" " + VAR_VAR_NAME + " " + OP_ASSIGNMENT + " " + VAR_VAR_VALUE; 
    public static final String IMPORTS_TEMPLATE = "$$IMPORT $$PACKAE_NAME";

    private String varType = "";
    private String varValue = "";
    private String varName = "";

    public void setVarType(String type){
        varType = type;
    }
    @Override
    public Expression buildExpression() {
        DeclareExpression exp = new DeclareExpression(buildVarExpression());
        return exp;
    }

    public void setVarValue(String i, String value) {
        varName = i.trim();
        varValue = value.trim();
    }
    public void appendImports(String s){
        
    }
    protected String buildVarExpression() {
        String exp = DECLARE_EXP_TEMPALTE;
        exp = exp.replace(VAR_VAR_TYPE, varType);
        exp = exp.replace(VAR_VAR_NAME, varName);
        if(varValue == "" || varValue == null)
        {
            exp = exp.replace(OP_ASSIGNMENT, "");
            
            return exp.replace(VAR_VAR_VALUE, "").trim();
        }
        return exp.replace(VAR_VAR_VALUE, varValue).trim();

    }
    
}
