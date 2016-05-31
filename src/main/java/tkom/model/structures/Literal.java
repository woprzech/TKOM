package tkom.model.structures;

import tkom.model.structures.data.Node;

/**
 * Created by wprzecho on 31.05.16.
 */
public class Literal extends Node {
    public int value;

    public void setValue(final int value) {
        this.value = value;
    }
}
