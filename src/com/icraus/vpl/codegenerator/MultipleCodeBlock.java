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
import javax.xml.bind.annotation.XmlTransient;
//@XmlRootElement
@XmlTransient
public class MultipleCodeBlock extends Statement {

    
    public static final String VAR_CDBLOCKS = "$$CD_BLCOKS";
    public static final String VAR_INIT_CDBLOCK = "$$INIT_CDBLCK";
    public static final String VAR_FINAL_CDBLOCK = "$$FINAL_CDBLCK";
    public static final String TEMPLATE_CDE_BLOCK = VAR_INIT_CDBLOCK
            + VAR_CDBLOCKS
            + VAR_FINAL_CDBLOCK;
    
    
    String template = TEMPLATE_CDE_BLOCK;
    private CodeBlock initalBlock = null;
    private List<CodeBlock> codeBlocks = new ArrayList<>();
    private CodeBlock finalBlock = null;
    public MultipleCodeBlock() {
    }

    @Override
    public String toText() throws ErrorGenerateCodeException {
        String res = null;
        try {
            res = getStatementTemplate();
            String initStr = getInitalBlock().toText();
            String finalStr = getFinalBlock().toText();
            List<String> bloStr = new ArrayList<>();
            List<CodeBlock> lst = getCodeBlocks();
            for(CodeBlock blk : lst){
                bloStr.add(blk.toText());
            }
            String blocksStr = String.join("", bloStr);
            res = res.replace(VAR_CDBLOCKS, blocksStr);
            res = res.replace(VAR_INIT_CDBLOCK, initStr);
            res = res.replace(VAR_FINAL_CDBLOCK, finalStr);
            return res.trim();
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new ErrorGenerateCodeException();
        }
    }

    public String getStatementTemplate() {
        return template;
    }
//    @XmlElementRef
    public CodeBlock getInitalBlock() {
        return initalBlock;
    }

    public void setInitalBlock(CodeBlock initalBlock) {
        this.initalBlock = initalBlock;
    }
//    @XmlElementRef
//    @XmlElementWrapper(name = "codeBlocks")
    public List<CodeBlock> getCodeBlocks() {
        return codeBlocks;
    }

    public void setCodeBlocks(List<CodeBlock> codeBlocks) {
        this.codeBlocks = codeBlocks;
    }
//    @XmlElementRef
    public CodeBlock getFinalBlock() {
        return finalBlock;
    }

    public void setFinalBlock(CodeBlock finalBlock) {
        this.finalBlock = finalBlock;
    }

}
