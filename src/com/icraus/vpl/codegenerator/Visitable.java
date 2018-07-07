package com.icraus.vpl.codegenerator;

public interface Visitable {
    public default void accept(StatementVisitor visitor){
        visitor.visit(this);
    }

}
