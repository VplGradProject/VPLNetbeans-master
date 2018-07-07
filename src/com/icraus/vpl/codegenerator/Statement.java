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
@XmlRootElement
public abstract class Statement  implements CodeGenerator{
    private String statementString = "";
    private CodeBlockImports imports = new CodeBlockImportsImpl();
    public int getFlags(){
        return 1;
    }
    public Statement() {
    }

    public Statement(String st) {
        statementString = st;
    }

    public String getStatementString() {
        return statementString;
    }

    public void setStatementString(String statementString) {
        this.statementString = statementString;
    }
    @XmlTransient
    public CodeBlockImports getImports() {
        return imports;
    }

    public void setImports(CodeBlockImports imports) {
        this.imports = imports;
    }
    public Statement toStatement() throws ErrorGenerateCodeException{
        return new SimpleStatement(toText());
    }
}
