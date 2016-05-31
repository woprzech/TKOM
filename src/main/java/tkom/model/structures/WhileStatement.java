package tkom.model.structures;

import tkom.model.structures.data.Node;

/**
 * Created by wprzecho on 30.05.16.
 */
public class WhileStatement extends Node {
    public Condition condition;
    public StatementBlock statementBlock;

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public void setStatementBlock(StatementBlock statementBlock) {
        this.statementBlock = statementBlock;
    }


}
