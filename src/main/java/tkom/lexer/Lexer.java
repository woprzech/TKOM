package tkom.lexer;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wprzecho on 11.05.16.
 */
public class Lexer {

    private InputStream inputStream;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private char nextChar() {
        char nextChar = 0;
        try {
            nextChar = (char) inputStream.read();
        } catch (IOException e) {
            System.out.println("[ERROR] during getting next char from input stream");
            e.printStackTrace();
        }
        return nextChar;
    }

    public




}
