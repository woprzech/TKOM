package parser

import tkom.lexer.Lexer
import tkom.model.TokenType
import tkom.model.structures.*
import tkom.parser.Parser

/**
 * Created by wprzecho on 30.05.16.
 */
class ParserSimpleTests extends AbstractParserTest {

    def "simple function"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){while(x == y && z <= t){}}")))
        Program program = parser.parse()
        expect:
        program.functions.size() == 1;
        Function function = program.functions.get(0)
        function.name == "funkcja"
        function.statementBlock.instrunctions.size() == 1
        function.statementBlock.instrunctions.get(0).class == WhileStatement.class
        WhileStatement statement = function.statementBlock.instrunctions.get(0)
        statement.condition.operands.size() == 1
        Condition operand = statement.condition.operands.get(0)
        operand.operator == TokenType.AND
    }

    def "simple function2"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){if(x == y || z <= t){}}")))
        Program program = parser.parse()
        expect:
        program.functions.size() == 1;
        Function function = program.functions.get(0)
        function.name == "funkcja"
        function.statementBlock.instrunctions.size() == 1
        function.statementBlock.instrunctions.get(0).class == IfStatement.class
        IfStatement statement = function.statementBlock.instrunctions.get(0)
        statement.condition.operands.size() == 2
        Condition condition = statement.condition
        condition.operator == TokenType.OR
    }

    def "simple function3"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){x=5;y=x;}")))
        Program program = parser.parse()
        expect:
        program.functions.size() == 1;
        Function function = program.functions.get(0)
        function.name == "funkcja"
        function.statementBlock.instrunctions.size() == 2
        function.statementBlock.instrunctions.get(0).class == AssingStatement.class
        function.statementBlock.instrunctions.get(1).class == AssingStatement.class
        AssingStatement statement = function.statementBlock.instrunctions.get(0)
    }

    def "funnCall test"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){funkcja1(5, y);}")))
        Program program = parser.parse()
        expect:
        program.functions.size() == 1;
        Function function = program.functions.get(0)
        function.name == "funkcja"
        function.statementBlock.instrunctions.size() == 1
        function.statementBlock.instrunctions.get(0).class == FunCall.class
        FunCall funCall = function.statementBlock.instrunctions.get(0)
        funCall.arguments.size() == 2
    }

    def "multiple functions"() {
        given:
        parser = new Parser(new Lexer(getInputStream("def funkcja(){}def funkcja1(){}def funkcja2(){}")))
        Program program = parser.parse()
        expect:
        program.functions.size() == 3;
    }

}