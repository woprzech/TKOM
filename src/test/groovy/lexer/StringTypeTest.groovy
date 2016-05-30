package lexer

import spock.lang.Shared
import tkom.lexer.Lexer
import tkom.model.TokenType

/**
 * Created by wprzecho on 16.05.16.
 */
class StringTypeTest extends AbstractTest {
    @Shared
    Lexer lexer

    def setupSpec() {
        lexer = new Lexer(getInputStream("a = \"Wojtek\";"))
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

    def "quotation mark"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.QUOTATION_MARK
    }

    def "string"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.ID
    }

    def "quotation mark2"() {
        when:
        token = lexer.nextToken()
        then:
        token.getType() == TokenType.QUOTATION_MARK
    }
}
