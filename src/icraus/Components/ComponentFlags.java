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
public class ComponentFlags { //TODO add functionalty
    public static final int DRAGGABLE_FLAG = 0b000001;
    public static final int PAGEABLE_FLAG = 0b000010;
    public static final int INSERTABLE_FLAG = 0b000100;
    public static final int SELECTABLE_FLAG = 0b001000;
    public static final int REMOVABLE_FLAG = 0b010000;
    public static final int CALLABLE_FLAG = 0b100000;
}
