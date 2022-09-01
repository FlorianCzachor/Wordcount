package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    static final int MY_TEXT_FILEPATH = 0;
    public static void main(String[] args) {
        String filePath = "";
        String text = "";

        if (args.length == 0) {
            text = getUserInputTextFromStandardIn();
        } else if (Files.exists(Path.of(args[MY_TEXT_FILEPATH]))){
            filePath = args[0];
        } else {
            System.out.println("File not found");
            return;
        }

        var wc = new WordCounter(text, filePath);
        var wordCount = wc.count();
        var uniqueWordCount = wc.uniqueWordCount();
        System.out.printf(String.format("Number of words: %d, unique: %d", wordCount, uniqueWordCount));
    }

    private static String getUserInputTextFromStandardIn() {
        try (var scanner = new Scanner(System.in)) {
            System.out.print("Enter text: ");
            return scanner.nextLine();
        }
    }
}
