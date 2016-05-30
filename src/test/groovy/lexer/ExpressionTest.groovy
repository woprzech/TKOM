package lexer;
import spock.lang.Shared
import tkom.lexer.Lexer
import tkom.model.TokenType

/**
 * Created by wprzecho on 16.05.16.
 */
class ExpressionTest extends AbstractTest {
    @Shared
    Lexer lexer

    def setupSpec() {
        lexer = new Lexer(getInputStream("a = 34;"))
    }

    def "id"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.ID
    }

    def "assign"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.ASSIGN
    }

    def "number"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.NUMBER
    }

    def "semicolon"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.SEMICOLON
    }
}
