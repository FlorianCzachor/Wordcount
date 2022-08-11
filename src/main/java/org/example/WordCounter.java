package org.example;
import java.util.*;
import java.io.*;

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
