package tkom.structures.model;

import tkom.structures.model.data.Node;

/**
 * Created by wprzecho on 31.05.16.
 */
public class Literal extends Node {
    public int value;

    public void setValue(final int value) {
        this.value = value;
    }

    public Type getType() {
        return Type.Literal;
    }

    public int getValue() {
        return value;
    }
}
