package tkom.model.structures;

import tkom.model.TokenType;
import tkom.model.structures.data.Node;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wprzecho on 31.05.16.
 */
public class Expression extends Node {
    public List<TokenType> operations = new LinkedList<>();

    public List<Node> operands = new LinkedList<>();

    public void addOperator(final TokenType operator) {
        operations.add(operator);
    }

    public void addOperand(final Node operand) {
        operands.add(operand);
    }
}
