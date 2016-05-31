package tkom.parser;

import tkom.lexer.Lexer;
import tkom.model.Token;
import tkom.model.TokenType;
import tkom.model.structures.*;
import tkom.model.structures.data.Node;

import java.util.LinkedList;
import java.util.List;

import static tkom.model.TokenType.*;

/**
 * Created by wprzecho on 30.05.16.
 */
public class Parser {
    private Lexer lexer;
    private Program program;
    private Token previousToken;

    public Parser(final Lexer lexer) {
        this.lexer = lexer;
    }

    public Program parse() {
        resetBufferedToken();
        program = new Program();
        Function lastParsedFunction = null;
        while ((lastParsedFunction = parseFunction()) != null) {
            program.addFunction(lastParsedFunction);
        }
        return program;
    }

    private Token accept(final TokenType... tokenTypes) {
        Token token;
        if (isBufferedToken()) {
            token = previousToken;
            resetBufferedToken();
        } else {
            token = lexer.nextToken();
        }
        if (isAcceptable(token, tokenTypes)) {
            return token;
        } else {
            final StringBuilder sb = new StringBuilder()
                    .append("Unexpected token: ")
                    .append(token.getText())
                    .append(" Expected: ");
            for (final TokenType tokenType : tokenTypes) {
                sb.append(tokenType);
            }
            System.out.println(sb.toString());
            try {
                throw new Exception("");
            } catch (Exception e) {
//                e.printStackTrace();
            }
            return token;
        }
    }

    private void resetBufferedToken() {
        previousToken = null;
    }

    private boolean isBufferedToken() {
        return previousToken != null;
    }

    private Token getBufferedToken() {
        return previousToken;
    }

    private boolean checkNextToken(final TokenType... tokenTypes) {
        if (!isBufferedToken()) {
            previousToken = lexer.nextToken();
        }
        return isAcceptable(previousToken, tokenTypes);
    }

    private boolean isAcceptable(final Token token, TokenType... tokenTypes) {
        for (final TokenType tokenType : tokenTypes) {
            if (tokenType.equals(token.getType())) {
                return true;
            }
        }
        return false;
    }

    private Function parseFunction() {
        System.out.println("Start processing function");
        final Function function = new Function();
        final Token startToken = accept(FUNCTION, END);
        if (!TokenType.FUNCTION.equals(startToken.getType())) {
            return null;
        }
        final Token functionName = accept(TokenType.ID);
        function.setName(functionName.getText());
        function.setParameters(parseParameters());
        function.setStatementBlock(parseStatementBlock());
        return function;
    }

    private List<String> parseParameters() {
        List<String> parameters = new LinkedList<>();
        accept(PARENTHESIS_OPEN);
        Token tempToken = accept(PARENTHESIS_CLOSE, ID);
        if (!tempToken.getType().equals(PARENTHESIS_CLOSE)) {
            parameters.add(tempToken.getText());
            while (true) {
                tempToken = accept(PARENTHESIS_CLOSE, COMMA);
                if (tempToken.getType().equals(PARENTHESIS_CLOSE)) {
                    break;
                }
                tempToken = accept(ID);
                parameters.add(tempToken.getText());
            }
        }

        return parameters;
    }

    private StatementBlock parseStatementBlock() {
        System.out.println("Parsing statement block...");
        final StatementBlock block = new StatementBlock();
        accept(BRACKET_OPEN);
        while (true) {
            if (!checkNextToken(IF, WHILE, RETURN, ID)) {
                break;
            }
            final Token tempToken = getBufferedToken();
            switch (tempToken.getType()) {
                case IF:
                    block.addInstruction(parseIfStatement());
                    break;
                case WHILE:
                    block.addInstruction(parseWhileStatement());
                    break;
                case RETURN:
                    block.addInstruction(parseReturnStatement());
                    break;
                case ID:
                    block.addInstruction(parseAssignStatementtOrFunnCall());
                    break;
                default:
                    break;
            }
        }
        accept(BRACKET_CLOSE);
        return block;
    }

    private Node parseAssignStatementtOrFunnCall() {
        System.out.println("Parsing assignment or funn call...");
        final Token tempToken = accept(ID);
        Node instruction = parseFunnCall(tempToken.getText());
        if (instruction == null) {
            final AssingStatement assingStatement = new AssingStatement();
            final Variable variable = new Variable();
            variable.setName(tempToken.getText());
            assingStatement.setVariable(variable);
            accept(ASSIGN);
            assingStatement.setValue(parseAssignable());
            instruction = assingStatement;
        }
        accept(SEMICOLON);
        return instruction;
    }

    private Node parseFunnCall(final String funnName) {
        System.out.print("Parsing funn call...");
        if (!checkNextToken(PARENTHESIS_OPEN)) {
            System.out.println("It is not a funn call");
            return null;
        }
        System.out.println("");
        final FunCall funnCall = new FunCall();
        funnCall.setName(funnName);
        accept(PARENTHESIS_OPEN);
        if (checkNextToken(PARENTHESIS_CLOSE)) {
            accept(PARENTHESIS_CLOSE);
        } else {
            while (true) {
                funnCall.addArgument(parseAssignable());
                if (checkNextToken(PARENTHESIS_CLOSE)) {
                    accept(PARENTHESIS_CLOSE);
                    break;
                } else if (checkNextToken(COMMA)) {
                    accept(COMMA);
//                    continue;
                } else {
                    accept(PARENTHESIS_CLOSE, COMMA);
                }
            }
        }
        return funnCall;


    }

    private Node parseAssignable() {
        System.out.println("Parsing assignable");
        Node assignable;
        if (checkNextToken(ID)) {
            final Token tempToken = accept(ID);
            assignable = parseFunnCall(tempToken.getText());
            if (assignable == null) {
                if (checkNextToken(SEMICOLON, PARENTHESIS_CLOSE)) {
                    assignable = new Variable();
                    ((Variable) assignable).setName(tempToken.getText());
                    return assignable;
                }
                assignable = parseExpression();
            }
        } else {
            assignable = parseExpression();
        }
        return assignable;
    }

    private Node parseExpression() {
        System.out.println("Parsing expression...");
        final Expression expression = new Expression();
        expression.addOperand(parseMultiplicativeExpression());
        while (checkNextToken(ADD, MINUS)) {
            final Token token = accept(ADD, MINUS);
            expression.addOperator(token.getType());
            expression.addOperand(parseMultiplicativeExpression());
        }
        return expression;
    }

    private Node parseMultiplicativeExpression() {
        System.out.println("Parsing multiplicative expression...");
        final Expression expression = new Expression();
        expression.addOperand(parsePrimaryExpression());
        while (checkNextToken(MULTIPLIER, DIV)) {
            final Token token = accept(MULTIPLIER, DIV);
            expression.addOperator(token.getType());
            expression.addOperand(parsePrimaryExpression());
        }
        return expression;
    }

    private Node parsePrimaryExpression() {
        System.out.println("Parsing primary expression...");
        Node expression = null;
        if (checkNextToken(PARENTHESIS_OPEN)) {
            accept(PARENTHESIS_OPEN);
            expression = parseExpression();
            accept(PARENTHESIS_CLOSE);
            return expression;
        } else if (checkNextToken(ID)) {
            expression = parseVariable();
        } else {
            expression = parseLiteral();
        }
        return expression;
    }

    private Node parseReturnStatement() {
        System.out.println("Parsing return...");
        final ReturnStatement returnStatement = new ReturnStatement();
        accept(RETURN);
        returnStatement.setReturnValue(parseAssignable());
        accept(SEMICOLON);
        return returnStatement;
    }

    private Node parseWhileStatement() {
        System.out.println("Parsing whileStatement...");
        final WhileStatement whileStatement = new WhileStatement();
        accept(WHILE);
        accept(PARENTHESIS_OPEN);
        whileStatement.setCondition(parseCondition());
        accept(PARENTHESIS_CLOSE);
        whileStatement.setStatementBlock(parseStatementBlock());
        return whileStatement;
    }

    private Node parseIfStatement() {
        System.out.println("Parsing if statement...");
        final IfStatement ifStatement = new IfStatement();
        accept(IF);
        accept(PARENTHESIS_OPEN);
        ifStatement.setCondition(parseCondition());
        accept(PARENTHESIS_CLOSE);
        ifStatement.setTrueBlock(parseStatementBlock());
        if (checkNextToken(ELSE)) {
            accept(ELSE);
            ifStatement.setElseBlock(parseStatementBlock());
        }
        return ifStatement;
    }

    private Condition parseCondition() {
        System.out.println("Parsing condition...");
        final Condition condition = new Condition();
        condition.addOperand(parseAndCondition());
        while (checkNextToken(OR)) {
            accept(OR);
            condition.setOperator(OR);
            condition.addOperand(parseAndCondition());
        }
        return condition;
    }

    private Node parseAndCondition() {
        System.out.println("Parsing and condition...");
        final Condition andCondition = new Condition();
        andCondition.addOperand(parseEqualityCondition());
        while (checkNextToken(AND)) {
            accept(AND);
            andCondition.setOperator(AND);
            andCondition.addOperand(parseEqualityCondition());
        }
        return andCondition;
    }

    private Node parseEqualityCondition() {
        System.out.println("Parsing equality condition...");
        final Condition equalityCondition = new Condition();
        equalityCondition.addOperand(parseRelationalCondition());
        while (checkNextToken(EQUALS, NOT_EQUALS)) {
            final Token token = accept(EQUALS, NOT_EQUALS);
            equalityCondition.setOperator(token.getType());
            equalityCondition.addOperand(parseRelationalCondition());
        }
        return equalityCondition;
    }

    private Node parseRelationalCondition() {
        System.out.println("Parsing relational condition...");
        final Condition equalityCondition = new Condition();
        equalityCondition.addOperand(parsePrimaryCondition());
        while (checkNextToken(LOWER, LOWER_EQUALS, GREATER, GREATER_EQUALS)) {
            final Token token = accept(LOWER, LOWER_EQUALS, GREATER, GREATER_EQUALS);
            equalityCondition.setOperator(token.getType());
            equalityCondition.addOperand(parsePrimaryCondition());
        }
        return equalityCondition;
    }

    private Node parsePrimaryCondition() {
        System.out.println("Parsing primary condition...");
        final Condition primaryCondition = new Condition();
        if (checkNextToken(NEGATION)) {
            accept(NEGATION);
            primaryCondition.setNegated(true);
        }
        if (checkNextToken(PARENTHESIS_OPEN)) {
            accept(PARENTHESIS_OPEN);
            primaryCondition.addOperand(parseCondition());
            accept(PARENTHESIS_CLOSE);
        } else if (checkNextToken(ID)) {
            primaryCondition.addOperand(parseVariable());
        } else {
            primaryCondition.addOperand(parseLiteral());
        }
        return primaryCondition;
    }

    private Node parseLiteral() {
        System.out.println("Parsing variable...");
        final Literal variable = new Literal();
        boolean isNegative = false;
        if (checkNextToken(MINUS)) {
            accept(MINUS);
            isNegative = true;
        }
        final Token token = accept(NUMBER);
        int value = Integer.valueOf(token.getText());
        if (isNegative)
            value *= -1;
        variable.setValue(value);

        return variable;
    }

    private Variable parseVariable() {
        System.out.println("Parsing literal...");
        final Variable variable = new Variable();
        final Token token = accept(ID);
        variable.setName(token.getText());
        return variable;
    }
}
