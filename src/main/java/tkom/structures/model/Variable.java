package tkom.structures.model;

import tkom.structures.model.data.Node;

/**
 * Created by wprzecho on 30.05.16.
 */
public class Variable extends Node {
    public String name;
    public int value;

    public void setName(final String name) {
        this.name = name;
    }

    public void setValue(final int value) {
        this.value = value;
    }

    public Type getType() {
        return Type.Variable;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
