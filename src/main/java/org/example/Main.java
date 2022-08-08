package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = "";
        String input = "";

        if (args.length == 0) {
            Scanner s = new Scanner(System.in);
            System.out.print("Enter text: ");
            input = s.nextLine();
        } else {
            filename = args[0];
        }
        Wordcount w = new Wordcount();
        int count = w.count(input, filename);
        System.out.println("Number of words: " + count);
    }
}
