/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MethodCodeBlockHead implements CodeBlockHead {

    private String methodName = "";
    private String returnType = "";
    
    private String accessType = "";
    private List<DeclareExpression> parameters = new ArrayList<>();
    
    @XmlElementRef
    @XmlElementWrapper(name = "parameters")
    public List<DeclareExpression> getParameters() {
        return parameters;
    }

    public void setParameters(List<DeclareExpression> parameters) {
        this.parameters = parameters;
    }
    public static final String RETURN_TYPE = "$$RETURN_TYPE";
    public static final String METHOD_NAME = "$$METHOD_NAME";
    public static final String PARAMETERS = "$$PARAMETERS";
    public static final String ACCESS_TYPE = "$$ACCESS";
    public static final String METHOD_TEMPLATE 
            = ACCESS_TYPE 
            + " " 
            + RETURN_TYPE
            + " "
            + METHOD_NAME
            + GrammerConstants.OP_PARAN_START
            + PARAMETERS
            + GrammerConstants.OP_PARAN_END;
    
    
    
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }
    public MethodCodeBlockHead(String _head){
        methodName  = _head;
    }

    public MethodCodeBlockHead() {
    }

    public MethodCodeBlockHead(String methodName, String returnType) {
        this.methodName = methodName;
        this.returnType = returnType;
        accessType = "";
    }
    
    public MethodCodeBlockHead(String methodName, String returnType, String access) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.accessType = access;
    }   
    
    @Override
    public String statementTemplateString() {
        return METHOD_TEMPLATE;
    }
    
    private List<String> toParamStringHelper(List<DeclareExpression> e){
        List<String> v = new ArrayList<>();
        for(DeclareExpression s : e){
            v.add(s.getExpressionStr());
        }
        return v;
    }
    @Override
    public String toText() throws ErrorGenerateCodeException {
        
        String res = statementTemplateString();
        
        String params = String.join(", ", toParamStringHelper(parameters));
        res = res.replace(PARAMETERS, params);
        res = res.replace(ACCESS_TYPE, getAccessType());
        res = res.replace(METHOD_NAME, methodName);
        res = res.replace(RETURN_TYPE, returnType);
        return res;
    }

    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    
}
