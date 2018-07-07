/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.ArrayList;
import java.util.List;


public class ConditionExpressionBuilder extends ExpressionBuilder {
    private List<ConditionExpression> conditionList = new ArrayList<>();
    private List<String> operator = new ArrayList<>();
    public ConditionExpressionBuilder() {
    }

    public void appendAndCondition(String cond) {
        ConditionExpression conds = new ConditionExpression(cond.trim());
        operator.add("&&");
        conditionList.add(conds);
    }

    public void appendOrCondition(String cond) {
        ConditionExpression conds = new ConditionExpression(cond.trim());
        operator.add("||");
        conditionList.add(conds);
    }

    public String buildConditionList() {
        List<String> strLst = new ArrayList<>();
        int size = conditionList.size();
        for(int i = 0; i < size; ++i){
            strLst.add(operator.get(i));
            strLst.add(conditionList.get(i).getExpressionStr());
        }
        strLst.remove(0);
        String res = String.join(" ", strLst);
        return res.trim();
    }

    @Override
    public Expression buildExpression() {
        ConditionExpression c = new ConditionExpression();
        c.setExpressionStr(buildConditionList());
        return c;
    }

    void appendAndCondition(ConditionExpression c) {
        String str = c.getExpressionStr();
        appendAndCondition(str);
    }

    void appendOrCondition(ConditionExpression c) {
        String str = c.getExpressionStr();
        appendOrCondition(str);
    }
}
