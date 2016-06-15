package tkom.structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class LiteralEx implements ExpressionOperand, ConditionOperand {
    private int value;
    private boolean isBool = false;

    @Override
    public boolean isTruthy() {
        return value == 1;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setIsBool(boolean isBool) {
        this.isBool = isBool;
    }

    public boolean isBool() {
        return isBool;
    }

    public int getValue() {
        return value;
    }

    public void plus(final LiteralEx sec) {
        value += sec.getValue();
    }

    public void minus(final LiteralEx sec) {
        value -= sec.getValue();
    }

    public void multi(final LiteralEx sec) {
        value *= sec.getValue();
    }

    public void div(final LiteralEx sec) {
        value /= sec.getValue();
    }

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        final LiteralEx literal = new LiteralEx();
        literal.setValue(value);
        return literal;
    }
}
