package org.daeskk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.daeskk.datastructures.Token;
import org.daeskk.enums.TokenType;
import org.daeskk.exceptions.LexicalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lexer {
    private String fileContent;
    private int currentPosition;
    private int state;

    public String readFile(String fileName) throws IOException {
        String fileContent = Files.readString(Paths.get(fileName));

        System.out.println("-------- DEBUG -------");
        System.out.println(fileContent);
        System.out.println("----------------------");

        return fileContent;
    }

    private boolean isNumber(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isChar(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    private boolean isOperator(char c) {
        return c == '>' || c == '<' || c == '=' || c == '!';
    }

    private boolean isSpace(char c) {
        return c == ' ' || c == '\t' || c == '\n'  || c == '\r';
    }

    private boolean isEOF() {
        return this.currentPosition == this.fileContent.length();
    }

    private boolean isEOF(char c) {
        return c == '\0';
    }

    private char nextChar() {
        if (isEOF()) {
            return '\0';
        }
        return this.fileContent.charAt(this.currentPosition++);
    }

    private void back() {
        this.currentPosition--;
    }

    private Token nextToken() {
        if (isEOF()) {
            return null;
        }

        StringBuilder term = new StringBuilder();

        state = 0;

        while (true) {
            char currentChar = nextChar();

            switch (state) {
                case 0:
                    if (isSpace(currentChar)) {
                        break;
                    }

                    term.append(currentChar);

                    if (isChar(currentChar)) {
                        state = 1;
                    }
                    else if (isNumber(currentChar)) {
                        state = 3;
                    }
                    else if (isOperator(currentChar)) {
                        state = 5;
                    }
                    else {
                        throw new LexicalException("Unrecognized Symbol");
                    }

                    break;

                case 1:
                    if (isChar(currentChar) || isNumber(currentChar)) {
                        term.append(currentChar);
                        state = 1;
                    }
                    else if (isSpace(currentChar) || isOperator(currentChar)) {
                        state = 2;
                    }
                    else {
                        throw new LexicalException("Malformed Identifier");
                    }

                    break;

                case 2:
                    if (!isEOF(currentChar))
                        back();

                    return new Token(
                            TokenType.IDENTIFIER,
                            term.toString()
                    );

                case 3:
                    if (isNumber(currentChar)) {
                        term.append(currentChar);
                    }
                    else if (!isChar(currentChar) || isEOF(currentChar)) {
                        return new Token(
                                TokenType.NUMBER,
                                term.toString()
                        );
                    }
                    else {
                        throw new LexicalException("Unrecognized Number");
                    }

                    break;

                case 5:
                    if (isOperator(currentChar)) {
                        term.append(currentChar);
                    }

                    return new Token(
                            TokenType.OPERATOR,
                            term.toString()
                    );

                default:
                    throw new LexicalException("Unknown error during lexical analysis");
            }
        }
    }


    public void execute(String filePath) {
        try {
            fileContent = this.readFile(filePath);

            Token token;

            do {
                token = nextToken();

                if (token != null) {
                    System.out.println(token);
                }

            } while (token != null);

        } catch (LexicalException e) {
            System.out.println("Lexical error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Generic error occurred: " + e.getMessage());
        }
    }
}
