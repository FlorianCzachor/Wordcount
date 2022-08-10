package org.example;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    final static int MY_TEXT_FILEPATH = 0;
    public static void main(String[] args) throws FileNotFoundException {
        String path = null;
        String input = null;

        if (args.length != 0 && Files.exists(Path.of(args[MY_TEXT_FILEPATH]))) {
            path = args[0];
        } else {
            var userInput = new Scanner(System.in);
            System.out.print("Enter text: ");
            input = userInput.nextLine();
            userInput.close();
        }

        var wc = new WordCounter();
        var wordCount = wc.count(input, path);
        System.out.println("Number of words: " + wordCount);
    }
}
