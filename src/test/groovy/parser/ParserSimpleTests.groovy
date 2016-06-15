package parser

import tkom.module.lexer.Lexer
import tkom.structures.model.AssingStatement
import tkom.structures.model.Condition
import tkom.structures.model.FunCall
import tkom.structures.model.Function
import tkom.structures.model.IfStatement
import tkom.structures.model.Program
import tkom.structures.model.data.TokenType
import tkom.module.parser.Parser
import tkom.structures.model.WhileStatement

/**
 * Created by wprzecho on 30.05.16.
 */
class ParserSimpleTests extends AbstractParserTest {

    def "while test"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){while(x == y && z <= t){}}")))
        when:
        Program program = parser.parse()
        then:
        Function function = program.functions.get(0)
        function.statementBlock.instructions.get(0).class == WhileStatement.class
        WhileStatement statement = function.statementBlock.instructions.get(0)
        statement.condition.operands.size() == 1
        Condition operand = statement.condition.operands.get(0)
        operand.operator == TokenType.AND
    }

    def "ifStatement test"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){if(x == y || z <= t){}}")))
        when:
        Program program = parser.parse()
        then:
        Function function = program.functions.get(0)
        function.statementBlock.instructions.get(0).class == IfStatement.class
        IfStatement statement = function.statementBlock.instructions.get(0)
        statement.condition.operands.size() == 2
        Condition condition = statement.condition
        condition.operator == TokenType.OR
    }

    def "assignments test"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){x=5;y=x;}")))
        when:
        Program program = parser.parse()
        then:
        Function function = program.functions.get(0)
        function.statementBlock.instructions.size() == 2
        function.statementBlock.instructions.get(0).class == AssingStatement.class
        function.statementBlock.instructions.get(1).class == AssingStatement.class
        AssingStatement statement = function.statementBlock.instructions.get(0)
    }

    def "funnCall test"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){funkcja1(5, y);}")))
        when:
        Program program = parser.parse()
        then:
        Function function = program.functions.get(0)
        function.statementBlock.instructions.size() == 1
        function.statementBlock.instructions.get(0).class == FunCall.class
        FunCall funCall = function.statementBlock.instructions.get(0)
        funCall.arguments.size() == 2
    }

    def "multiple functions"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){}def funkcja1(){}def funkcja2(){}")))
        when:
        Program program = parser.parse()
        then:
        program.functions.size() == 3;
    }

}