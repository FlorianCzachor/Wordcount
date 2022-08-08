package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.String.format;

public class Main {

    private static final int INDEX_OF_USER_INPUT_FILE = 0;

    public static void main(String[] args) {
        if (args.length == INDEX_OF_USER_INPUT_FILE) {
            System.out.print("Enter text: ");
            Scanner s = new Scanner(System.in);
            String userInput = s.nextLine();

            int count = new WordCounter().count(userInput);

            System.out.println("Number of words: " + count);
        } else {
            Path userInputFile = Paths.get(format("src/main/resources/%s", args[INDEX_OF_USER_INPUT_FILE]));
            int count = new WordCounter().count(userInputFile);

            System.out.println("Number of words: " + count);
        }
    }
}
