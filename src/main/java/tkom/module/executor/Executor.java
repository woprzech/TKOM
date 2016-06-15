package tkom.module.executor;

import tkom.structures.ex.FunctionEx;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Executor {
    public void execute(final List<FunctionEx> functions) {
        final Map<String, FunctionEx> definedFunctions = new HashMap<>();
        functions.forEach(function -> definedFunctions.put(function.getName(), function));
        final FunctionEx mainFuntion = definedFunctions.get("main");
        mainFuntion.execute(null, definedFunctions, new LinkedList<>());
    }
}
