package org.example;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.lang.String.format;

public class Main {

    private static final int INDEX_OF_USER_INPUT_FILE = 0;
    private static final String RESOURCES_DIRECTORY_PATH = "src/main/resources/%s";

    public static void main(String[] args) {
        if (args.length == INDEX_OF_USER_INPUT_FILE) {
            System.out.print("Enter text: ");
            Scanner s = new Scanner(System.in);
            String userInput = s.nextLine();

            int count = new WordCounter().countWords(userInput);

            System.out.println("Number of words: " + count);
        } else {
            Path userInputFile = Paths.get(format(RESOURCES_DIRECTORY_PATH, args[INDEX_OF_USER_INPUT_FILE]));
            int count = new WordCounter().countWords(userInputFile);

            System.out.println("Number of words: " + count);
        }
    }
}
