/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class WhileCodeBlockHead extends IfCodeBlockHead {
    public static final String WHILE_TEMPLATE = IF_TEMPLATE.replace(GrammerConstants.STAT_IF, GrammerConstants.WHILE_STAT);  

    public WhileCodeBlockHead() {
        super(WHILE_TEMPLATE);
    }
    
}
