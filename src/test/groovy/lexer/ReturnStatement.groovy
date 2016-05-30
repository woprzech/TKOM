package lexer

import spock.lang.Shared
import tkom.lexer.Lexer
import tkom.model.TokenType

/**
 * Created by wprzecho on 16.05.16.
 */
class ReturnStatement extends AbstractTest {
    @Shared
    Lexer lexer

    def setupSpec() {
        lexer = new Lexer(getInputStream("return a;"))
    }

    def "return"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.RETURN
    }

    def "id"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.ID
    }

    def "semicolon"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.SEMICOLON
    }
}
