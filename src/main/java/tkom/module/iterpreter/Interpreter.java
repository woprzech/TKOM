package tkom.module.iterpreter;

import tkom.module.executor.Executor;
import tkom.module.lexer.Lexer;
import tkom.module.parser.Parser;
import tkom.module.semcheck.SemCheck;
import tkom.structures.ex.FunctionEx;
import tkom.structures.model.Program;

import java.io.InputStream;
import java.util.List;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Interpreter {
    private Lexer lexer;
    private Parser parser;
    private SemCheck semCheck;
    private Executor executor;

    public Interpreter(final InputStream stream) {
        lexer = new Lexer(stream);
        parser = new Parser(lexer);
        Program program = null;
        try {
            System.out.println("==============================PARSER======================================");
            program = parser.parse();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        semCheck = new SemCheck();
        executor = new Executor();
        try {
            System.out.println("===============================SEM CHECK=======================================");
            final List<FunctionEx> functions = semCheck.check(program);
            System.out.println("===============================EXECUTING=======================================");
            executor.execute(functions);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
