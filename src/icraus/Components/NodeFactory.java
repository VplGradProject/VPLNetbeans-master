package icraus.Components;

@FunctionalInterface
public interface NodeFactory {
    public Selectable createNode(Component c) throws IllegalNodeInstantiation;
}
