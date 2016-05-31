package tkom.model.structures;

import tkom.model.structures.data.Node;

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

}
