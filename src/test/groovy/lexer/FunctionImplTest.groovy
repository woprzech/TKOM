package lexer

import spock.lang.Shared
import tkom.module.lexer.Lexer
import tkom.structures.model.data.TokenType

/**
 * Created by wprzecho on 16.05.16.
 */
class FunctionImplTest extends AbstractTest {
    @Shared
    Lexer lexer

    def setupSpec() {
        lexer = new Lexer(getInputStream("def() {if(x==y){}}"))
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

    def "condition"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.IF
        setNextToken()
        token.getType() == TokenType.PARENTHESIS_OPEN
        setNextToken()
        token.getType() == TokenType.ID
        setNextToken()
        token.getType() == TokenType.EQUALS
        setNextToken()
        token.getType() == TokenType.ID
        setNextToken()
        token.getType() == TokenType.PARENTHESIS_CLOSE
    }

    def "brackets"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.BRACKET_OPEN
        setNextToken()
        token.getType() == TokenType.BRACKET_CLOSE
    }

    def "bracket close"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.BRACKET_CLOSE
    }
}
