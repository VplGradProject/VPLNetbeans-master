/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icraus.Components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shoka
 */
public class NewEmptyJUnitTest {
    static  class Person{
        String name;
        int id;

        public Person() {
        }

        public Person(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return id + " " + name;
        }
        
    }
    public NewEmptyJUnitTest() {
    }
    @Test
    public void testAPI(){
        List<Person> lst = Arrays.asList(new Person("Ahmed", 10),
                new Person("Zaki", 100),
                new Person("Zaki", 50),
                new Person("Zaki", 60),
                new Person("Mahmoud", 50));
        Person c = lst.stream().reduce(new Person("", 0),(a, b) ->{
            a.id += b.id;
            a.name += b.name;
            return a;
        });
        String str = Arrays.asList(("Hello"), ("Bad")).stream().map(e->{
            return e.split("");
        }).flatMap(e->{
            return Arrays.stream(e);
        }).collect(Collectors.joining(","));
        System.out.println(str);
        
        System.out.println(c);
        
        
        
    }
    enum S{
        i(0), x(5);

        S(int i) {
        
        }
        
    }
    
}
