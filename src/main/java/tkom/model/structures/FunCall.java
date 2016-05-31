package tkom.model.structures;

import tkom.model.structures.data.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wprzecho on 30.05.16.
 */
public class FunCall extends Node {
    public String name;
    public List<Node> arguments = new LinkedList<>();

    public void setName(final String name) {
        this.name = name;
    }

    public void addArgument(final Node argument) {
        arguments.add(argument);
    }

}
