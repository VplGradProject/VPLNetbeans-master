/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icraus.vpl.codegenerator;

import java.util.Objects;
import java.util.UUID;


public abstract class SimpleParserRule implements ParserRule {
    private String uuid = UUID.randomUUID().toString();
    abstract public void applyParsing(SimplePropertyStatement statment, String text) throws IllegalStatementException;    

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.uuid);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        final SimpleParserRule other = (SimpleParserRule) obj;
        if (!Objects.equals(this.uuid, other.uuid)) {
            return false;
        }
        return true;
    }
    
}
