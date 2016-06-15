package tkom.module.lexer;

import tkom.structures.model.data.PredefinedTokens;
import tkom.structures.model.data.Token;
import tkom.structures.model.data.TokenType;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by wprzecho on 11.05.16.
 */
public class Lexer {

    private InputStream inputStream;
    private char tokenChar;
    private int lineCounter = 1;
    private int charCounter = 1;

    public Lexer(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    private char getNextChar() throws Exception {
        char nextChar = 0;
        try {
            nextChar = (char) inputStream.read();
            if (nextChar == '\n') {
                lineCounter++;
                charCounter = 1;
                return getNextChar();
            } else {
                charCounter++;
            }
        } catch (IOException e) {
            throw new Exception("[ERROR] during getting next char from input stream");
        }
        return nextChar;
    }

    public Token nextToken() throws Exception {
        final StringBuilder sb = new StringBuilder();
        final Token token = new Token();
        if (tokenChar == 0 || Character.isSpaceChar(tokenChar))
            while (!isEndOfStream() && Character.isSpaceChar(tokenChar = getNextChar()))
                ;
        if (Character.isSpaceChar(tokenChar)) {
            token.setType(TokenType.UNDEFINED);
            return token;
        }
        sb.append(tokenChar);
        if (Character.isDigit(tokenChar)) {
            processNumber(sb, token);
        } else if (Character.isLetter(tokenChar)) {
            processIdOrKeyWord(sb, token);
        } else {
            processOperator(sb, token);
        }
        token.setText(sb.toString());
        return token;
    }

    private void processOperator(StringBuilder sb, Token token) throws Exception {
        if (isSecondCharOperatorAvailable(tokenChar, tokenChar = getNextChar())) {
            sb.append(tokenChar);
            tokenChar = getNextChar();
        }
        final TokenType operatorType = PredefinedTokens.OPERATORS.get(sb.toString());

        if (operatorType != null)
            token.setType(operatorType);
        else if (isEndOfStream()) {
            token.setType(TokenType.END);
        } else
            token.setType(TokenType.UNDEFINED);
    }

    private void processIdOrKeyWord(StringBuilder sb, Token token) throws Exception {
        while (Character.isLetter(tokenChar = getNextChar()) || Character.isDigit(tokenChar)) {
            sb.append(tokenChar);
        }
        if (PredefinedTokens.KEYWORDS.containsKey(sb.toString())) {
            token.setType(PredefinedTokens.KEYWORDS.get(sb.toString()));
        } else {
            token.setType(TokenType.ID);
        }
    }

    private void processNumber(StringBuilder sb, Token token) throws Exception {
        while (Character.isDigit(tokenChar = getNextChar())) {
            sb.append(tokenChar);
        }
        token.setType(TokenType.NUMBER);
    }

    private boolean isSecondCharOperatorAvailable(char firstOpChar, char secondOpChar) {
        return (secondOpChar == '=' && (firstOpChar == '<' || firstOpChar == '>' || firstOpChar == '!' || firstOpChar == '='))
                || (firstOpChar == '&' && secondOpChar == '&')
                || (firstOpChar == '|' && secondOpChar == '|');
    }

    private boolean isEndOfStream() throws Exception {
        try {
            return inputStream.available() == 0;
        } catch (IOException e) {
            throw new Exception("[ERROR] during cheking end of stream");
        }
    }

    public int getLineCounter() {
        return lineCounter;
    }

    public int getCharCounter() {
        return charCounter;
    }
}
