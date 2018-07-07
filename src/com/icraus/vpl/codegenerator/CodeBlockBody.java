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


/**
 *
 * @author Shoka
 */
@XmlRootElement
public class CodeBlockBody implements CodeGenerator{
    private List<Statement> children = new ArrayList<>();
    
    @XmlElementRef
    @XmlElementWrapper(name = "statementChildren")
    public List<Statement> getChildren() {
        return children;
    }

    public void setChildren(List<Statement> children) {
        this.children = children;
    }

    @Override
    public String toText() throws ErrorGenerateCodeException{
        String res = "";
        for(Statement s : children){
            res += s.toText();
            res += "\n";
        }
        return res;
    }
    
}
