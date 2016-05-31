package tkom.model.structures;

import tkom.model.structures.data.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wprzecho on 30.05.16.
 */
public class StatementBlock extends Node {
    public List<Node> instrunctions = new LinkedList<>();

    public void addInstruction(final Node instruction) {
        instrunctions.add(instruction);
    }
}
