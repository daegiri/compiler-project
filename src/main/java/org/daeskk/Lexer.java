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
import java.util.function.Predicate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lexer {
    private String fileContent;
    private int currentPosition;
    private int state;
    private List<Token> tokens = new ArrayList<>();

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

    private char nextChar() {
        return this.fileContent.charAt(this.currentPosition++);
    }

    private void back() {
        this.currentPosition--;
    }

    private String readWhile(Predicate<Character> predicate) {
        StringBuilder result = new StringBuilder();

        while (currentPosition < fileContent.length()
                && predicate.test(fileContent.charAt(currentPosition))) {
            result.append(fileContent.charAt(currentPosition++));
        }

        return result.toString();
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
                    if (isChar(currentChar)) {
                        term.append(currentChar);
                        state = 1;
                    }

                    else if (isNumber(currentChar)) {
                        state = 3;
                    }

                    else if (isSpace(currentChar)) {
                        state = 0;
                    }

                    else if (isOperator(currentChar)) {
                        state = 5;
                    }

                    else {
                        throw new LexicalException("Unrecognized SYMBOL");
                    }

                    break;

                case 1:
                    if (isChar(currentChar) || isNumber(currentChar)) {
                        term.append(currentChar);
                        state = 1;
                    } else {
                        state = 2;
                    }
                    break;

                case 2:
                    back();

                    return new Token(
                            TokenType.IDENTIFIER,
                            term.toString()
                    );


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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
