package tkom.model.structures;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by wprzecho on 30.05.16.
 */
public class Program {
    public List<Function> functions = new LinkedList<>();

    public void addFunction(final Function function) {
        functions.add(function);
    }
}
