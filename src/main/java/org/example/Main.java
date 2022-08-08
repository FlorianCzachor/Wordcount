package org.example;

import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            Scanner s = new Scanner(System.in);
            System.out.print("Enter text: ");
            String input = s.nextLine();
            Wordcount w = new Wordcount();
            int count = w.count(input);
            System.out.println("Number of words: " + count);
        } else {
            Wordcount w = new Wordcount();
            int count = w.count(Paths.get("src/main/resources/" + args[0]));
            System.out.println("Number of words: " + count);
        }
    }
}
