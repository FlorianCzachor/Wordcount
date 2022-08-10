package org.example;
import java.util.*;
import java.io.*;

// CleanCode: Avoid Negations & Always Use Braces
public class WordCounter {
    final static String STOP_WORDS_PATH = "src/main/resources/stopwords.txt";

    public int count(String userInput, String myTextFilepath) throws FileNotFoundException {
        Objects.requireNonNull(userInput, "Input must not be null");
        var wordCounter = new ArrayList<String>();
        var stopWords = 0;

        // CleanCode: Avoid NullPointerException in Conditionals
        // checks if path is empty then uses user input otherwise use the mytext.txt file
        if (myTextFilepath == null) {
            var inputWords = userInput.split(" ");
            wordCounter = checkWords(inputWords);
        } else {
            var myText = new File(myTextFilepath);
            var readFile = new Scanner(myText);
            while (readFile.hasNextLine()) {
                var data = readFile.nextLine();
                var inputWords = data.split(" ");
                var temp = checkWords(inputWords);
                wordCounter.addAll(temp);
            }
            readFile.close();
        }
        stopWords = stopWords(wordCounter);
        return wordCounter.size() - stopWords;
    }

    // Method checkWords checks if the words only consist of letters and returns a list of valid words
    private ArrayList<String> checkWords(String[] inputWords) {
        var wordCounter = new ArrayList<String>();
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
                wordCounter.add(word);
            }
        }
        return wordCounter;
    }

    // Method stopWords counts how many times a word of stopwords.txt is inside ArrayList result
    private int stopWords(List<String> result) throws FileNotFoundException {
        var stopWords = new File(STOP_WORDS_PATH);
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
