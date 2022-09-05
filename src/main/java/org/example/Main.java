package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {

    public static final int INDEX_OF_FILE = 0;

    public static void main(String[] args) {
        String filePath = "";
        String text = "";

        if (args.length == 0) {
            text = getUserInputTextFromStandardIn();
        } else if (Files.exists(Path.of(args[INDEX_OF_FILE]))){
            filePath = args[INDEX_OF_FILE];
        } else {
            System.out.println("File not found");
            return;
        }

        var wordCounter = new WordCounter(text, filePath);
        var wordCount = wordCounter.countWords();
        var uniqueWordCount = wordCounter.uniqueWordCount();
        System.out.printf(String.format("Number of words: %d, unique: %d", wordCount, uniqueWordCount));
    }

    private static String getUserInputTextFromStandardIn() {
        try (var scanner = new Scanner(System.in)) {
            System.out.print("Enter text: ");
            return scanner.nextLine();
        }
    }
}
