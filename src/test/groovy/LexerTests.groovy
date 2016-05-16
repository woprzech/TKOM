import spock.lang.Specification
import tkom.lexer.Lexer
import tkom.model.Token
import tkom.model.TokenType

import java.nio.charset.StandardCharsets

class AbstractTest extends Specification {
    Token token

    def InputStream getInputStream(final String string) {
        return new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
    }

    def setNextToken(){
        token = lexer.nextToken()
    }
}

class LexerTest extends AbstractTest {
    Lexer lexer

    def "white spaces test"() {
        given:
        lexer = new Lexer(getInputStream("               "))

        expect:
        lexer.nextToken() == null;
    }

    def "number test"() {
        given:
        lexer = new Lexer(getInputStream(" 1231231"))
        token = lexer.nextToken()
        expect:
        token.getType() == TokenType.NUMBER
        token.getText().equals("1231231")
    }

    def "id test1"() {
        given:
        lexer = new Lexer(getInputStream(" zmi2enna1"))
        token = lexer.nextToken()
        expect:
        token.getType() == TokenType.ID
        token.getText().equals("zmi2enna1")
    }

    def "id test2"() {
        given:
        lexer = new Lexer(getInputStream(" Zmienna1"))
        token = lexer.nextToken()
        expect:
        token.getType() == TokenType.ID
        token.getText().equals("Zmienna1")
    }

    def "operator = test"() {
        given:
        lexer = new Lexer(getInputStream("="))
        token = lexer.nextToken()
        expect:
        token.getType() == TokenType.ASSIGN
        token.getText().equals("=")
    }

    def "two chars operator test"() {
        given:
        lexer = new Lexer(getInputStream("<="))
        token = lexer.nextToken()
        expect:
        token.getType() == TokenType.LOWER_EQUALS
        token.getText().equals("<=")
    }

}

