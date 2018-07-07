/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CodeBlockImportsImpl implements CodeBlockImports {
    private List<String> packageLst = new ArrayList<>();
    @Override
    public String toText() {
        List<String> lst = new ArrayList<>();
        for(String s : packageLst){
            lst.add(lineBuilder(s));

        }
        
        String res = String.join(GrammerConstants.END_LINE, lst);
        return res;
    }

    protected String lineBuilder(String packageName){
        String res = GrammerConstants.STAT_IMPORT + " " + packageName + " " + GrammerConstants.OP_END_LINE;
        return res.trim();
    }
    @Override
    @XmlElementWrapper(name = "packageList")
    public List<String> getPackageLst() {
        return packageLst;
    }

    @Override
    public void setPackageLst(List<String> packageLst) {
        this.packageLst = packageLst;
    }
    
}
