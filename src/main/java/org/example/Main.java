package org.example;

import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.print("Enter text: ");
            Scanner s = new Scanner(System.in);
            String userInput = s.nextLine();

            Wordcount w = new Wordcount();
            int count = w.count(userInput);

            System.out.println("Number of words: " + count);
        } else {
            Wordcount w = new Wordcount();
            int count = w.count(Paths.get("src/main/resources/" + args[0]));

            System.out.println("Number of words: " + count);
        }
    }
}
