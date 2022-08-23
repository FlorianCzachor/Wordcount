package org.example;
import java.util.*;
import java.io.*;

/**
 * A word counter can count the number of words as well as the number of unique words.
 * To know more about which words exactly are counted see {@link WordCounter#count()}
 * <p>
 * <ul>
 * <li> Words can either be counted from a .txt file, or from user console input.
 * <li> The stop words file contains words, that should be excluded from the counting.
 * <li> Only alphabetic words, words containing with a '-' or a '.' are counted.
 * <li> The count is never negative.
 * </ul>
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
     * They contain alphabetic letters from (A-Z, a-z) (=> see {@link WordCounter#checkWords(String[])}) and are separated by white-space(s).
     * Hyphens (-) and Periods (.) are also valid and are converted into white-spaces in order to count the words.
     * Other words are not counted.
     * <p>
     * Stop words are predefined words in the requirements and are stored in a text file named "stopwords.txt".
     * These words are: "on", "the", "off", "a"
     * If the file can't be loaded, stop words are ignored.
     * <p>
     * Example:
     * <pre>
     *    inputText => word count ( + unique word count {@link WordCounter#uniqueWordCount()})
     *           "word" => 1
     *      "word word" => 2
     *     "word word." => 2
     * "word word-word" => 3
     *     "word wo3rd" => 1 // "3" not in A-Z or a-z
     *     "word, word" => 1 // "," not in A-Z or a-z
     *        "word on" => 1 // "on" is a stop word
     * </pre>
     *
     * @return number words containing alphabetic letters that are separated by white-space(s)
     * and are not stop words
     * @throws NullPointerException if userInput or myTextFilePath is null
     * @see WordCounter#checkWords(String[]) check if words are alphabetic letters from (A-Z, a-z)
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

    /**
     * Counts number of unique words from already filtered words Arraylist.
     * These words get passed through a HashSet, so that only one of every word exists.
     * <p>
     * The size of that list is subtracted by the number of unique stop words.
     * You can find more information on stop words here: {@link WordCounter#count()}
     * <p>
     * Example:
     * <pre>
     *    inputText => word count {@link WordCounter#count()} + unique word count
     *              "hello" => 1 + 1
     *        "hello hello" => 2 + 1
     *        "hello world" => 2 + 2
     * "hello on the world" => 2 + 2 // "on" and "the" are stop words
     * </pre>
     *
     * @return number of valid alphabetic words subtracted by the number of unique stop words
     * @see WordCounter#count()
     */
    public int uniqueWordCount() {
        var uniqueCount = new HashSet<>(wordCounter);
        return uniqueCount.size() - uniqueStopWords.size();
    }
}
