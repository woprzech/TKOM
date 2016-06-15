package tkom.structures.model;

import tkom.structures.model.data.Node;

/**
 * Created by wprzecho on 30.05.16.
 */
public class AssingStatement extends Node {
    public Variable variable;
    public Node value;

    public void setVariable(final Variable variable) {
        this.variable = variable;
    }

    public void setValue(final Node value) {
        this.value = value;
    }

    public Type getType() {
        return Type.Assignment;
    }

}
