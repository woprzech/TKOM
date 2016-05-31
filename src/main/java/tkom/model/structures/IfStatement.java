package tkom.model.structures;

import tkom.model.structures.data.Node;

/**
 * Created by wprzecho on 30.05.16.
 */
public class IfStatement extends Node {
    public Condition condition;
    public StatementBlock trueBlock;
    public StatementBlock elseBlock;

    public void setCondition(final Condition cond) {
        this.condition = cond;
    }

    public void setTrueBlock(final StatementBlock trueBlock) {
        this.trueBlock = trueBlock;
    }

    public void setElseBlock(final StatementBlock elseBlock) {
        this.elseBlock = elseBlock;
    }

}
