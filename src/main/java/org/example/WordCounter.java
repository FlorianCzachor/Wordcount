package org.example;
import java.util.*;
import java.io.*;

// CleanCode: Avoid Negations & Always Use Braces
public class WordCounter {

    public int count(String input, String path) throws FileNotFoundException {
        Objects.requireNonNull(input, "Input must not be null");
        var wordCount = new ArrayList<String>();
        var stopWords = 0;

        // CleanCode: Avoid NullPointerException in Conditionals
        // checks if path is empty then uses user input otherwise use the mytext.txt file
        if (path == null) {
            var inputWords = input.split(" ");
            wordCount = checkWords(inputWords);
        } else {
            var fileContent = new File(path);
            var readFile = new Scanner(fileContent);
            while (readFile.hasNextLine()) {
                var data = readFile.nextLine();
                var inputWords = data.split(" ");
                var temp = checkWords(inputWords);
                wordCount.addAll(temp);
            }
            readFile.close();
        }
        stopWords = stopWords(wordCount);
        return wordCount.size() - stopWords;
    }

    // Method checkWords checks if the words only consist of letters and returns a list of valid words
    private ArrayList<String> checkWords(String[] inputWords) {
        var fileContent = new ArrayList<String>();
        // CleanCode: Favor For-Each Over For Loops
        for (var word : inputWords) {
            var onlyLetters = true;
            for (var i=0; i<word.length(); i++) {
                if (!Character.isAlphabetic(word.charAt(i))) {
                    onlyLetters = false;
                }
            }
            if (word.equals("")) {
                continue;
            }
            if (onlyLetters) {
                fileContent.add(word);
            }
        }
        return fileContent;
    }

    // Method stopWords counts how many times a word of stopwords.txt is inside ArrayList result
    private int stopWords(List<String> result) throws FileNotFoundException {
        var stopWords = new File("src/main/resources/stopwords.txt");
        var words = 0;

        var readFile = new Scanner(stopWords);
        while (readFile.hasNextLine()) {
            var data = readFile.nextLine();
            var inputWords = data.split(" ");
            for (var inputWord : inputWords) {
                for (var s : result) {
                    if (s.equals(inputWord)) {
                        words++;
                    }
                }
            }
        }
        readFile.close();
        return words;
    }
}
