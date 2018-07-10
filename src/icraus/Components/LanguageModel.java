/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.parsers.ErrorChoosingLanguage;
import com.icraus.vpl.codegenerator.parsers.TemplateCodeGenerator;
import com.sun.javafx.collections.ObservableListWrapper;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Shoka
 */
public class LanguageModel {

    private static LanguageModel instance = new LanguageModel();
    private ObservableList<LanguageComponent> languageList = new ObservableListWrapper<>(new ArrayList<>());

    public static LanguageModel getInstance() {
        return instance;
    }
    private TemplateCodeGenerator templateCodeGeneratorInstance;

    public ObservableList<LanguageComponent> getLanguageList() {
        return languageList;
    }

    private LanguageModel() {
        languageList.add(new LanguageComponent("Java", TemplateCodeGenerator.JAVA_GRAMMAR_PROP_FILE, "JAVA", ".java"));
        languageList.add(new LanguageComponent("Python", TemplateCodeGenerator.PYTHON_GRAMMAR_PROP_FILE, "PYTHON", ".py"));
        languageList.add(new LanguageComponent("C++", TemplateCodeGenerator.CPP_GRAMMAR_PROP_FILE, "CPP", ".cpp"));
        
    }
    public void LanguageChanged(LanguageComponent comp) throws ErrorChoosingLanguage{
        templateCodeGeneratorInstance = TemplateCodeGenerator.getInstance();
        templateCodeGeneratorInstance.setGenerationLanguage(comp.getLanguageId());
        templateCodeGeneratorInstance.setGENERATION_EXTENSION(comp.getLanguageExtension());
        templateCodeGeneratorInstance.load(comp.getType(), comp.getLanguageGrammerPath());
    }

    

}
