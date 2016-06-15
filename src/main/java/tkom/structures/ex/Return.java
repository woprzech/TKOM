package tkom.structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Return extends Instruction {
    private Assignable value;

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        return value.execute(scope, functions);
    }

    public void setValue(final Assignable value) {
        this.value = value;
    }

    @Override
    public boolean canReturn() {
        return true;
    }
}