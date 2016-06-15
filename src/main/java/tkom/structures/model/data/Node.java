package tkom.structures.model.data;

/**
 * Created by wprzecho on 31.05.16.
 */
public abstract class Node {
    protected Node parent;

    public enum Type {
        Assignment,
        Call,
        Condition,
        Expression,
        FunDefinition,
        IfStatement,
        Program,
        ReturnStatement,
        StatementBlock,
        VarDeclaration,
        Literal,
        Variable,
        WhileStatement
    }

    public abstract Type getType();


}
