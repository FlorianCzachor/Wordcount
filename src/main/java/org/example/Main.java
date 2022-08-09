package org.example;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String path = null;
        String input = null;

        if (args.length != 0 && Files.exists(Path.of(args[0]))) {
            path = args[0];
        } else {
            Scanner s = new Scanner(System.in);
            System.out.print("Enter text: ");
            input = s.nextLine();
            s.close();
        }
        Wordcount w = new Wordcount();
        int count = w.count(input, path);
        System.out.println("Number of words: " + count);
    }
}
