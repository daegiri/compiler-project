package org.daeskk.enums;

public enum TokenType {
    IDENTIFIER,             // (a..z) (A..Z|0..9|a..z)* -> camel case
    NUMBER,                 // (0..9)+
    PUNCTUATION,            // ;
    OPERATOR,               // > | >= | < | <= | == | !=
    ASSIGN,                 // =

    PLUS_SYMBOL,
    MINUS_SYMBOL,
    MULTIPLY_SYMBOL,
    DIVIDE_SYMBOL
}
