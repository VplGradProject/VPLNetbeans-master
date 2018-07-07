/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Shoka
 */
@XmlRootElement
public class UiProperties {

    private double width = 0.0;
    private double height = 0.0;
    private String css = "";
    private String cssId = "";

    public static UiProperties createUiProperties(double width, double height, String css, String cssId) {
        UiProperties properties = new UiProperties(width, height, css, cssId);
        return properties;
    }

    public UiProperties() {

    }

    public UiProperties(double _width, double _height, String _css, String _cssId) {
        setWidth(_width);
        setHeight(_height);
        setCss(_css);
        setCssId(_cssId);
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getCss() {
        return css;
    }

    public void setCss(String css) {
        this.css = css;
    }

    public String getCssId() {
        return cssId;
    }

    public void setCssId(String cssId) {
        this.cssId = cssId;
    }
}
