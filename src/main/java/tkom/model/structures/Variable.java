package tkom.model.structures;

import tkom.model.structures.data.Node;

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
}
