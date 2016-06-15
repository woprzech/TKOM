package lexer

import spock.lang.Shared
import tkom.module.lexer.Lexer
import tkom.structures.model.data.TokenType

/**
 * Created by wprzecho on 16.05.16.
 */
class IfExpressionTest extends AbstractTest {
    @Shared
    Lexer lexer

    def setupSpec() {
        lexer = new Lexer(getInputStream("if(a==b){}else{}"))
    }

    def "if"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.IF
    }

    def "condition"() {
        when:
        token = lexer.nextToken()
        then:
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

    def "else"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.ELSE
    }

    def "brackets2"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.BRACKET_OPEN
        setNextToken()
        token.getType() == TokenType.BRACKET_CLOSE
    }
}
