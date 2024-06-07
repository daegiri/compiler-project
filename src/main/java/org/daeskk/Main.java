package org.daeskk;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();

        System.out.println(lexer);

        lexer.execute("C:\\Users\\arthur\\IdeaProjects\\compiler\\src\\input.isi");
    }
}