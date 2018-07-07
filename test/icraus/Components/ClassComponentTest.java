/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.IllegalStatementException;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;

/**
 *
 * @author Shoka
 */
public class ClassComponentTest extends Application {

    public ClassComponentTest() {
    }

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test
    public void testSomeMethod() throws IllegalStatementException {

        try {
            ClassComponent c = new ClassComponent("AA", "BB");
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream("F:\\1.bin"));
            MethodComponent m = new MethodComponent("method", "void", "public");
            c.addComponent(m);
            m.addComponent(ComponentPluginFactories.createIfStatement());
            stream.writeObject(c);
            stream.close();
            ObjectInputStream inSt = new ObjectInputStream(new FileInputStream("F:\\1.bin"));
            Component e = (ClassComponent) inSt.readObject();
            System.out.println(e);
            
            XMLEncoder ee = new XMLEncoder(new BufferedOutputStream(new FileOutputStream("F:\\1.xml")));
            ee.writeObject(c);
            ee.close();
            XMLDecoder d = new XMLDecoder(new BufferedInputStream(new FileInputStream("F:\\1.xml")));
            ClassComponent E = (ClassComponent) d.readObject();
            String className = E.getClassName();
            MethodComponent get = (MethodComponent) E.childernProperty().get(0);
            System.out.println(className);
//            System.out.println(get.getMethodName());
            Component get1 = get.getChildern().get(0);
            System.out.println(get1.getType());
            System.out.println(get1);

            d.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ClassComponentTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClassComponentTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClassComponentTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalComponent ex) {
            Logger.getLogger(ClassComponentTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
    }

}
