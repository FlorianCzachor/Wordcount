package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    final static int MY_TEXT_FILEPATH = 0;
    public static void main(String[] args) {
        String path = "";
        String input = "";

        if (args.length == 0) {
            var userInput = new Scanner(System.in);
            System.out.print("Enter text: ");
            input = userInput.nextLine();
            userInput.close();
        } else if (Files.exists(Path.of(args[MY_TEXT_FILEPATH]))){
            path = args[0];
        } else {
            System.out.println("File not found");
            return;
        }

        var wc = new WordCounter(input, path);
        var wordCount = wc.count();
        var uniqueWordCount = wc.uniqueWordCount();
        System.out.printf(String.format("Number of words: %d, unique: %d", wordCount, uniqueWordCount));
    }
}
