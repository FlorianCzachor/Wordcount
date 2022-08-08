package org.example;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String filename = "";
        String input = "";

        if (args.length != 0) {
            filename = args[0];
        } else {
            Scanner s = new Scanner(System.in);
            System.out.print("Enter text: ");
            input = s.nextLine();
        }
        Wordcount w = new Wordcount();
        int count = w.count(input, filename);
        System.out.println("Number of words: " + count);
    }
}
