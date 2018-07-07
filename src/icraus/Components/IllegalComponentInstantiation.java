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
public class IllegalComponentInstantiation extends Exception {

    public IllegalComponentInstantiation() {
        this("Can't create Component");
    }
    public IllegalComponentInstantiation(String s) {
        super(s);
    }
    
}
