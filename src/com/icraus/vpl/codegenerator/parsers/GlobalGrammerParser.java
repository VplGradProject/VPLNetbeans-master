/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator.parsers;

import com.icraus.vpl.codegenerator.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shoka
 */
public class GlobalGrammerParser{

    private static GlobalGrammerParser instance = new GlobalGrammerParser();
    private JavaCodeGenerator jGrammar = new JavaCodeGenerator();
    
    public boolean isClassType(CodeGenerator c) {
        try {
            CodeBlock blk = (CodeBlock) c;
            return (blk.getHead() instanceof ClassCodeBlockHead);
        } catch (ClassCastException e) {
            return false;
        }
    }
    private List<Statement> code = new ArrayList<>();

    public static GlobalGrammerParser getInstance() {
        return instance;
    }

    public void generateCode(String path) throws ErrorGenerateCodeException {
        code.parallelStream().forEach(c -> {

            if (isClassType(c)) {
                try {
                    String res = "";
                    res = c.toText();
                    String v = ((ClassCodeBlockHead) ((CodeBlock) c).getHead()).getClassName();
//                jGrammar.createFile();
                    jGrammar.generateClass(path, v, res);
                } catch (ErrorGenerateCodeException ex) {
                    Logger.getLogger(GlobalGrammerParser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

    }

//        return res;
    public List<Statement> getCode() {
        return code;
    }

    public void setCode(List<Statement> code) {
        this.code = code;
    }



}
