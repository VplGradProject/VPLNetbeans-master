/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import com.icraus.vpl.codegenerator.CodeBlock;
import com.icraus.vpl.codegenerator.CodeBlockBody;
import com.icraus.vpl.codegenerator.CodeBlockHead;
import com.icraus.vpl.codegenerator.SimplePropertyStatement;
import com.icraus.vpl.codegenerator.Statement;
import javafx.beans.Observable;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public class SimpleComponentTabbed extends SimpleComponent implements Pageable {

    private Tab tab;

    public SimpleComponentTabbed() {
        tab = new Tab("", new ScrollAnchorPane(this));
        tab.textProperty().bind(componentStringProperty());
    }

    public SimpleComponentTabbed(SimplePropertyStatement s, Node delegate, String _type) {
        super(s, delegate, _type);
        tab = new Tab(_type, new ScrollAnchorPane(this));
        childernProperty().addListener((Observable e) -> {
            contentChanged();
        });

    }

    private void contentChanged() {
        SimplePropertyStatement s = (SimplePropertyStatement) getStatement();
        s.getChildren().clear();
        for (Component c : getChildern()) {
            s.getChildren().add(c.getStatement());
        }

    }

    public SimpleComponentTabbed(Statement s, Node delegate, String _type) {
        super(s, delegate, _type);
        tab = new Tab(_type, new ScrollAnchorPane(this));
    }

    public SimpleComponentTabbed(CodeBlockHead head, Node delegate, String _type) {
        super(null, delegate, _type);
        tab = new Tab("", new ScrollAnchorPane(this));
        CodeBlock blk = new CodeBlock();
        blk.setHead(head);
        blk.setBody(new CodeBlockBody());
//        setStatement(blk);
        childernProperty().addListener((Observable e) -> {
            CodeBlockBody body = blk.getBody();
            body.getChildren().clear();
            for (Component c : childernProperty()) {
                body.getChildren().add(c.getStatement());
            }
        });

    }

    @Override
    public int getFlags() {
        return super.getFlags() | ComponentFlags.PAGEABLE_FLAG; //To change body of generated methods, choose Tools | Templates.
    }

    @XmlTransient
    @Override
    public Tab getTab() {
        return tab;
    }

}
