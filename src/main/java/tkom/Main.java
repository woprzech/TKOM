package tkom;

import tkom.helper.FileHelper;
import tkom.module.iterpreter.Interpreter;

import java.io.InputStream;

/**
 * Created by wprzecho on 11.06.16.
 */
public class Main {

    public static void main(String[] args) {
        for (final String string : args) {
            System.out.println(string);
        }
        final InputStream inputStream = FileHelper.getInputStreamFromFile(args[0]);
        final Interpreter interpreter = new Interpreter(inputStream);
    }
}
