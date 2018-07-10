/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator.parsers;

import com.icraus.vpl.codegenerator.ErrorGenerateCodeException;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import icraus.Components.LanguageComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shoka
 */
public class TemplateCodeGenerator {

    public static final String JAVA_GRAMMAR_PROP_FILE = "JavaGrammar.properties";
    public static final String CPP_GRAMMAR_PROP_FILE = "CPPGrammar.properties";
    public static final String PYTHON_GRAMMAR_PROP_FILE = "PythonGrammar.properties";

    public static TemplateCodeGenerator getInstance() {
        return instance;
    }

    public static String getGENERATION_EXTENSION() {
        return GENERATION_EXTENSION;
    }

    public static void setGENERATION_EXTENSION(String aGENERATION_EXTENSION) {
        GENERATION_EXTENSION = aGENERATION_EXTENSION;
    }
    Map<String, Properties> grammarMap = new HashMap<>();
    private Properties grammar;
    private static final TemplateCodeGenerator instance = new TemplateCodeGenerator();
    private static String GENERATION_EXTENSION = ".java";

    private TemplateCodeGenerator() {
        try {
            loadFromResHelper(JAVA_GRAMMAR_PROP_FILE);
            loadFromResHelper(PYTHON_GRAMMAR_PROP_FILE);
            loadFromResHelper(CPP_GRAMMAR_PROP_FILE);
        } catch (ErrorChoosingLanguage ex) {
            Logger.getLogger(TemplateCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load(String languageGrammerPath) throws ErrorChoosingLanguage {
        load(LanguageComponent.FILE_Type.RESOURCE, languageGrammerPath);
    }

    public void load(LanguageComponent.FILE_Type type, String languageGrammerPath) throws ErrorChoosingLanguage {
        Properties get = grammarMap.get(languageGrammerPath);
        if(get != null){
            grammar = get;
        }
        if (type == LanguageComponent.FILE_Type.RESOURCE) {
            loadFromResHelper(languageGrammerPath);
        } else  {

            loadFromFileHelper(languageGrammerPath);
        }
    }

    private void loadFromFileHelper(String path) throws ErrorChoosingLanguage {
        File f = new File(path);
        try {
            FileInputStream s = new FileInputStream(f);
            loadHelper(s, path);
        } catch (FileNotFoundException ex) {
            throw new ErrorChoosingLanguage("No Such File");
        }
    }

    private void loadHelper(InputStream stream, String path) throws ErrorChoosingLanguage {
        try {
            grammar = new Properties();
            grammarMap.put(path, grammar);
            grammar.load(stream);
        } catch (IOException ex) {
            Logger.getLogger(TemplateCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErrorChoosingLanguage("Can't Load Properties");
        }
    }

    private void loadFromResHelper(String lang) throws ErrorChoosingLanguage {
        try (InputStream stream = getClass().getResourceAsStream(lang)) {
            loadHelper(stream, lang);
        } catch (IOException ex) {
            Logger.getLogger(TemplateCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErrorChoosingLanguage("No Such Resource");
        }

    }

    public Properties getGrammar() {
        return grammar;
    }

    /*public String indent(String code) {
        Pattern p = null;//Pattern.compile(PROPERTIES_START_VAR + ANY_PATTERN + PROPERTIES_END_VAR);
        Matcher matcher = null;//p.matcher(text);
        p = Pattern.compile("[$]{2}BLOCK_START_OP" + ANY_PATTERN + "[$]{2}BLOCK_END_OP");

        matcher = p.matcher(code);
//        while (matcher.find()) {
//            String lang = matcher.group(1);
//        }

    }*/
    public String generateCode(String code) {
        Set<Object> gram = grammar.keySet();
//        code = indent(code);
        for (Object s : gram) {
            code = code.replace((String) s, (String) grammar.get(s));
        }
        return code;
    }

// TODO   public void setGrammar(Properties grammar) {
//        this.grammar = grammar;
//    }
    public void generateClass(String path, String v, String res) throws ErrorGenerateCodeException {
        Path paths = Paths.get(path, v + GENERATION_EXTENSION);
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
            Logger.getLogger(TemplateCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErrorGenerateCodeException();
        } catch (IOException ex) {
            Logger.getLogger(TemplateCodeGenerator.class.getName()).log(Level.SEVERE, null, ex);
            throw new ErrorGenerateCodeException();
        }
//        OutputStream stream = 
//        f.
        generateCode(res);
    }

    public void setGenerationLanguage(String languageId) {
        SimplePropertyStatement.setGenerationLanguage(languageId);
    }

}
