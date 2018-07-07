/*
 * To change this license head, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.List;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class CodeBlock extends Statement {

    public static final String HEAD_STAT = "$$HEAD_STAT";
    public static final String BODY_STAT = "$$BODY_STAT";
    public static final String CODE_BLOCK_TEMPLATE
            = HEAD_STAT
            + GrammerConstants.OP_BLOCK_START
            + GrammerConstants.END_LINE
            + BODY_STAT
            //            + GrammerConstants.END_LINE
            + GrammerConstants.OP_BLOCK_END;

    private CodeBlockHead head = null;
    private CodeBlockBody body = null;
//TODO    private CodeBlockImports imports = null;
    @XmlElementRefs({
        @XmlElementRef(type = ClassCodeBlockHead.class)
    ,
        @XmlElementRef(type = MethodCodeBlockHead.class)
    })

    public CodeBlockHead getHead() {
        return head;
    }

    public void setHead(CodeBlockHead head) {
        this.head = head;
    }

    @XmlElementRef
    public CodeBlockBody getBody() {
        return body;
    }

    public void setBody(CodeBlockBody body) {
        this.body = body;
    }

    public static CodeBlock createCodeBlock(CodeBlockHead head) {
        CodeBlock b = new CodeBlock();
        b.setBody(new CodeBlockBody());
        b.setHead(head);
        return b;
    }

    public static CodeBlock createClassCodeBlock(String name, String packageName, String access) {

        ClassCodeBlockHead h = new ClassCodeBlockHead();
        h.setClassName(name);
        h.setPackageName(packageName);
        h.setAccessType(access);
        return createCodeBlock(h);
    }

    public static CodeBlock createMethodCodeBlock(String name, String returnS, String access) {

        MethodCodeBlockHead h = new MethodCodeBlockHead(name, returnS, access);
        return createCodeBlock(h);
    }

    @Override
    public String toText() throws ErrorGenerateCodeException {
        String res = CODE_BLOCK_TEMPLATE;
        try {
            res = res.replace(HEAD_STAT, head.toText());
            res = res.replace(BODY_STAT, body.toText());
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw new ErrorGenerateCodeException();
        }
        return res.trim();
    }

}
