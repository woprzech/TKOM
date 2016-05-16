import spock.lang.Shared
import tkom.lexer.Lexer
import tkom.model.TokenType

/**
 * Created by wprzecho on 16.05.16.
 */
class FunctionImplTest extends AbstractTest {
    @Shared
    Lexer lexer

    def setupSpec() {
        lexer = new Lexer(getInputStream("def() {}"))
    }

    def "function"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.FUNCTION
    }

    def "parenth open"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.PARENTHESIS_OPEN
    }

    def "parenth close"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.PARENTHESIS_CLOSE
    }

    def "bracket open"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.BRACKET_OPEN
    }

    def "bracket close"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.BRACKET_CLOSE
    }
}
