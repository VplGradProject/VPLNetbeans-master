/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.List;

/**
 *
 * @author Shoka
 */

public interface CodeBlockImports extends CodeGenerator {

    public List<String> getPackageLst();

    public void setPackageLst(List<String> packageLst);
    
}
