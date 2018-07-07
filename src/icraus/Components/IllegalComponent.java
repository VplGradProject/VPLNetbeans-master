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
public class IllegalComponent extends Exception {

    public IllegalComponent() {
        this("Can't add this Component");
    }
    public IllegalComponent(String s) {
        super(s);
    }
    
}
