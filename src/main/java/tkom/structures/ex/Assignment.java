package tkom.structures.ex;

import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Assignment extends Instruction {

    private String name;
    private Assignable value;

    @Override
    public LiteralEx execute(final Scope scope, final Map<String, FunctionEx> functions) {
        scope.setVariableValue(name, value.execute(scope, functions));
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Assignable getValue() {
        return value;
    }

    public void setValue(final Assignable value) {
        this.value = value;
    }
}
