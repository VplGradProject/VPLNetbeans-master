/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator.parsers;

import com.icraus.vpl.codegenerator.ErrorGenerateCodeException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shoka
 */
public class JavaCodeGenerator {

    public static final String JAVA_GRAMMAR_PROP_FILE = "JavaGrammar.properties";
    private Properties grammar;

    public JavaCodeGenerator() {
        try (InputStream stream = getClass().getResourceAsStream(JAVA_GRAMMAR_PROP_FILE)) {
            grammar = new Properties();
            grammar.load(stream);

        } catch (IOException ex) {
            Logger.getLogger(JavaCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Properties getGrammar() {
        return grammar;
    }

    public String generateCode(String code) {
        Set<Object> gram = grammar.keySet();
        for (Object s : gram) {
            code = code.replace((String) s, (String) grammar.get(s));
        }
        return code;
    }

// TODO   public void setGrammar(Properties grammar) {
//        this.grammar = grammar;
//    }
   
    public void generateClass(String path, String v, String res) throws ErrorGenerateCodeException {
        Path paths = Paths.get(path, v + ".java");
//        Files.createDirectories(dir, attrs)
        File f = paths.toFile();

        try (FileWriter writer = new FileWriter(f)) {
            if (!f.exists()) {
                f.getParentFile().mkdir();
                f.createNewFile();
            }
            String cc = generateCode(res);
            writer.write(cc);
            

        } catch (FileNotFoundException ex) {
            Logger.getLogger(JavaCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErrorGenerateCodeException();
        } catch (IOException ex) {
            Logger.getLogger(JavaCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErrorGenerateCodeException();
        }
//        OutputStream stream = 
//        f.
        generateCode(res);
    }
}
