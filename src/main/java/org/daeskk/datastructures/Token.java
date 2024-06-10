package org.daeskk.datastructures;

import org.daeskk.enums.TokenType;

public record Token(
    TokenType type,
    String text
) {

    @Override
    public String toString() {
        return "Token {" +
                "type: " + type +
                ", text: '" + text + '\'' +
                '}';
    }
}
