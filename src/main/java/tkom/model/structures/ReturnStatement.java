package tkom.model.structures;

import tkom.model.structures.data.Node;

/**
 * Created by wprzecho on 30.05.16.
 */
public class ReturnStatement extends Node {
    public Node returnValue;

    public void setReturnValue(final Node value) {
        this.returnValue = value;
    }
}
