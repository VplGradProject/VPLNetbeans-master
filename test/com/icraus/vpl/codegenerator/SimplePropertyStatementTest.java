/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shoka
 */
public class SimplePropertyStatementTest {
    
    public SimplePropertyStatementTest() {
    }
    
    @Test
    public void testSimpleStatmentTypical() throws ErrorGenerateCodeException {
        String expected = "$$IF_STAT $$PARAN_START_OP x >= 10 $$PARAN_END_OP $$BLOCK_START_OP\n"
                + "while(x < 10){\n"
                + "	x++;\n"
                + "}"
                + "$$BLOCK_END_OP";
        SimplePropertyStatement stmnt = new SimplePropertyStatement("$$IF_STAT $$PARAN_START_OP %%CONDTION%% $$PARAN_END_OP $$BLOCK_START_OP\n"
                + "%%CHILDREN_TOKEN%%"
                + "$$BLOCK_END_OP");
        stmnt.getPropertyMap().put("%%CONDTION%%", "x >= 10");
        stmnt.getChildren().add(new SimpleStatement("while(x < 10){\n"
                + "	x++;\n"
                + "}"));
        System.out.println(expected);
        System.out.println("-------------------------------");
        String res = stmnt.toText();
        System.out.println(res);
        assertEquals(expected, res);
    }

    @Test
    public void testfromFile() throws IllegalStatementException, ErrorGenerateCodeException, JAXBException {
        SimplePropertyStatement s = SimplePropertyStatement.fromFile(".\\templates\\IF_TEMPLATE.template");
        s.getChildren().add(new SimpleStatement("while(x < 10){\n"
                + "	x++;\n"
                + "}"));
//        System.out.println(s.toText());
System.out.println(s.getName());
        JAXBContext c = JAXBContext.newInstance(SimplePropertyStatement.class, Statement.class);
        
        c.createMarshaller().marshal(s, System.out);
        System.out.println("---------------------------");
    }
}
