package tkom.model;

import com.google.common.collect.ImmutableMap;

import static tkom.model.TokenType.*;

/**
 * Created by wprzecho on 16.05.16.
 */
public class PredefinedTokens {
    public static final ImmutableMap<String, TokenType> KEYWORDS = ImmutableMap.<String, TokenType>builder()
            .put("def", FUNCTION)
            .put("if", IF)
            .put("while", WHILE)
            .put("else", ELSE)
            .put("return", RETURN)
            .build();
    public static final ImmutableMap<String, TokenType> OPERATORS = ImmutableMap.<String, TokenType>builder()
            .put("(", PARENTHESIS_OPEN)
            .put(")", PARENTHESIS_CLOSE)
            .put("{", BRACKET_OPEN)
            .put("}", BRACKET_CLOSE)
            .put("<", LOWER)
            .put("<=", LOWER_EQUALS)
            .put(">", GREATER)
            .put(">=", GREATER_EQUALS)
            .put("==", EQUALS)
            .put("!=", NOT_EQUALS)
            .put("+", ADD)
            .put("-", MINUS)
            .put("*", MULTIPLIER)
            .put("/", DIV)
            .put(";", SEMICOLON)
            .put("=", ASSIGN)
            .put(",", COMMA)
            .put("&&", AND)
            .put("||", OR)
            .put("!", NEGATION)
            .put("\"", QUOTATION_MARK)
            .build();
}
