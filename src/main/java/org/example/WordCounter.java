package org.example;
import java.util.*;
import java.io.*;

/**
 * A word counter can count the number of words as well as the number of unique words.
 *
 * <p>
 * Words can either be counted from a .txt file, or from user console input.
 * The stop words file contains words, that should be excluded from the counting.
 * Only alphabetic words, words containing with a '-' or a '.' are counted.
 * The count is never negative.
 */
public class WordCounter {
    final static String STOP_WORDS_PATH = "src/main/resources/stopwords.txt";
    public String userInput;
    public String myTextFilepath;
    public ArrayList<String> wordCounter;
    public HashSet<String> uniqueStopWords;

    public WordCounter(String userInput, String myTextFilepath) {
        this.userInput = userInput;
        this.myTextFilepath = myTextFilepath;
        this.wordCounter = new ArrayList<>();
        this.uniqueStopWords = new HashSet<>();
    }

    /**
     * Counts number of words from .txt file or console user input.
     *
     * <p>
     * Only counts alphabetic words, words containing with a '-' or a '.'
     * Example: ToDo: How to
     *
     * @return words that were counted
     * @see WordCounter#checkWords() check words validity ToDo: How to
     */
    public int count() {
        Objects.requireNonNull(userInput, "UserInput must not be null");
        Objects.requireNonNull(myTextFilepath, "Filepath must not be null");

        try {
            if (myTextFilepath.isEmpty()) {
                userInput = userInput.replaceAll("[-.]", " ");
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
        } catch (FileNotFoundException fnfe) {
            System.out.println("File was not found.");
        }
        return wordCounter.size() - stopWords();
    }

    private ArrayList<String> checkWords(String[] inputWords) {
        var wordCounter = new ArrayList<String>();
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

    private int stopWords() {
        var stopWords = new File(STOP_WORDS_PATH);
        var words = 0;

        try {
            var readFile = new Scanner(stopWords);
            while (readFile.hasNextLine()) {
                var data = readFile.nextLine();
                var inputWords = data.split(" ");
                for (var inputWord : inputWords) {
                    for (var s : wordCounter) {
                        if (s.equals(inputWord)) {
                            words++;
                            uniqueStopWords.add(s);
                        }
                    }
                }
            }
            readFile.close();
        } catch (FileNotFoundException fnfe) {
            System.out.println("File was not found.");
        }
        return words;
    }

    public int uniqueWordCount() {
        var uniqueCount = new HashSet<>(wordCounter);
        return uniqueCount.size() - uniqueStopWords.size();
    }
}
