/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ForCodeBlockHead implements CodeBlockHead {

    public static final String  VAR_INIT_LOCAL_VAR = "$$INIT_LOCAL_VAR";
    public static final String  VAR_COND = "$$CONDITION";
    public static final String  VAR_POST_ITR = "$$POST_ITR";
    public static String FOR_TEMPLATE = GrammerConstants.STAT_FOR
            + GrammerConstants.OP_PARAN_START
            + VAR_INIT_LOCAL_VAR
            + GrammerConstants.OP_END_LINE
            + " " + VAR_COND
            + GrammerConstants.OP_END_LINE
            + " "+ VAR_POST_ITR
            + GrammerConstants.OP_PARAN_END;

    private ConditionExpression condition = new ConditionExpression();

    private DeclareExpression declareExpression;
    private OperationExpression postItrExpression;
    private String forStatementText;

    public ForCodeBlockHead() {
        this.forStatementText = FOR_TEMPLATE;
    }
    protected ForCodeBlockHead(String template) {
        this.forStatementText = template;
    }
    @Override
    public String toText() throws ErrorGenerateCodeException {
        String condStr = condition.getExpressionStr();
        String postStr = postItrExpression.getExpressionStr();
        String initLocStr = declareExpression.getExpressionStr();
        String result = statementTemplateString().replace(VAR_INIT_LOCAL_VAR, initLocStr);
        result = result.replace(VAR_COND, condStr);
        result = result.replace(VAR_POST_ITR, postStr);
        return result;
    }
    

    public ConditionExpression getCondition() {
        return condition;
    }

    public void setCondition(ConditionExpression condition) {
        this.condition = condition;
    }

    public void setDeclareExpression(DeclareExpression dex) {
        this.declareExpression = dex;
    }

    @XmlElementRef
    public DeclareExpression getDeclareExpression() {
        return declareExpression;
    }
    public void setPostItrExpression(OperationExpression op) {
        this.postItrExpression = op;
    }
    
    @XmlElementRef
    public OperationExpression getPostItrExpression() {
        return postItrExpression;
    }

    @Override
    public String statementTemplateString() {
        return new String(forStatementText);
    }


}
