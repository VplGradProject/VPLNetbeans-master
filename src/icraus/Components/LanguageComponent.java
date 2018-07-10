/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

/**
 *
 * @author Shoka
 */
public class LanguageComponent {
    private String languageName = "";
    private String languageGrammerPath = "";
    private String languageId = "";
    private String languageExtension = "";
    private FILE_Type type = FILE_Type.RESOURCE;

    public LanguageComponent() {
    }

    public LanguageComponent(String _langName, String _langGrammerPath, String _langId, String _ext) {
        languageGrammerPath = _langGrammerPath;
        languageName = _langName;
        languageId = _langId;
        languageExtension = _ext;
    }

    public LanguageComponent(String _langName, String _langGrammerPath, String _langId, String _langext, FILE_Type type) {
        this(_langName, _langGrammerPath, _langId, _langext);
        setType(type);
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public String getLanguageGrammerPath() {
        return languageGrammerPath;
    }

    public void setLanguageGrammerPath(String languageGrammerPath) {
        this.languageGrammerPath = languageGrammerPath;
    }

    public String getLanguageId() {
        return languageId;
    }

    public void setLanguageId(String languageId) {
        this.languageId = languageId;
    }

    public FILE_Type getType() {
        return type;
    }

    public void setType(FILE_Type type) {
        this.type = type;

    }

    public String getLanguageExtension() {
        return languageExtension;
    }

    public void setLanguageExtension(String languageExtension) {
        this.languageExtension = languageExtension;
    }

    public enum FILE_Type {
        FILE, RESOURCE
    }

    @Override
    public String toString() {
        return languageName;
    }

}
