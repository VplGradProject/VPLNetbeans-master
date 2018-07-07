/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IfCodeBlockHead implements CodeBlockHead {
    private ConditionExpression condition = new ConditionExpression();
    public static final String VAR_CONDITION = "$$CONDTION";
    public static final String IF_TEMPLATE = 
            GrammerConstants.STAT_IF
            + GrammerConstants.OP_PARAN_START
            + VAR_CONDITION
            + GrammerConstants.OP_PARAN_END;
    private String statementString;

    public IfCodeBlockHead() {
        this(IF_TEMPLATE);
    }
    protected IfCodeBlockHead(String template) {
        this.statementString = template;
    }
    @Override
    public String toText() throws ErrorGenerateCodeException {
        String condStr = condition.getExpressionStr();
        String res = statementTemplateString();
        res = res.replace(VAR_CONDITION, condStr);
        
        return res;
    }
    @XmlElementRef
    public ConditionExpression getCondition() {
        return condition;
    }

    public void setCondition(ConditionExpression condition) {
        this.condition = condition;
    }

    @Override
    public String statementTemplateString() {
        return statementString;
    }
    
}
