package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;

/**
 * A word counter can count the number of words as well as the number of unique words.
 * To know more about which words exactly are counted see {@link WordCounter#countWords()} and {@link WordCounter#countUniqueWords()}
 * <p>
 * <ul>
 * <li> Words can either be counted from a .txt file, or from user console input.
 * <li> The stop words file contains words, that are excluded from the counting.
 * <li> Only words consisting of alphabetic letters from (A-Z, a-z) are counted.
 * <li> The count is never negative.
 * </ul>
 */
public class WordCounter {
    private static final String STOP_WORDS_PATH = "src/main/resources/stopwords.txt";
    private String text;
    private final String filePath;

    /**
     * Creates a word counter with either text or a filePath.
     *
     * @param text text from user console input.
     * Must not be null, can be empty.
     * @param filePath filepath of mytext.txt file.
     * Must not be null, can be empty.
     * @throws NullPointerException if text or filePath is null
     */
    public WordCounter(String text, String filePath) {
        Objects.requireNonNull(text, "Text must not be null");
        Objects.requireNonNull(filePath, "FilePath must not be null");

        this.text = text;
        this.filePath = filePath;
    }

    /**
     * Counts the number of words from a user console input or a .txt file which you need to set in the constructor, for more information {@link WordCounter#WordCounter(String text, String filePath)}.
     * They consist of alphabetic letters from (A-Z, a-z) (=> see {@link WordCounter#filterWordsContainingAlphabeticCharsOnly(String[])}) and are separated by white-space(s).
     * Other words are not counted.
     * <p>
     * Predefined stop words are excluded. Stop words are defined in the requirements and are stored in a text file named "stopwords.txt".
     * These words are: "on", "the", "off", "a"
     * If the file can't be loaded, stop words are ignored.
     * <p>
     * Example:
     * <pre>
     *        inputText => word count
     *           "word" => 1
     *      "word word" => 2
     *     "word word." => 2
     * "word word-word" => 3
     *
     *     "word wo3rd" => 1 // "3" not in A-Z or a-z
     *     "word, word" => 1 // "," not in A-Z or a-z
     *        "word on" => 1 // "on" is a stop word
     * </pre>
     *
     * @return number of words containing alphabetic letters
     * that are separated by white-space(s)
     * and are not stop words
     * @throws NullPointerException if text or filePath is null
     * @see WordCounter#filterWordsContainingAlphabeticCharsOnly(String[]) check if words are alphabetic letters from (A-Z, a-z)
     * @see WordCounter#countUniqueWords() count the number of unique words
     */
    public int countWords() {
        var words = new ArrayList<>(separateWordsByWhitespaces());
        var stopWords = new ArrayList<>(stopWords(words));
        return words.size() - stopWords.size();
    }

    /**
     * Counts the number of unique words.
     * <p>
     * Example:
     * <pre>
     *            inputText => word count {@link WordCounter#countWords()} + unique word count
     *              "hello" => 1 + 1
     *        "hello hello" => 2 + 1
     *        "hello world" => 2 + 2
     * "hello on the world" => 2 + 2 // "on" and "the" are stop words
     * </pre>
     *
     * @return unique number of words containing alphabetic letters
     * that are separated by white-space(s)
     * subtracted by the number of unique stop words
     * @throws NullPointerException if text or filePath is null
     * @see WordCounter#filterWordsContainingAlphabeticCharsOnly(String[]) check if words are alphabetic letters from (A-Z, a-z)
     * @see WordCounter#countWords() count number of words from a .txt file or user console input
     */
    public int countUniqueWords() {
        var words = new ArrayList<>(separateWordsByWhitespaces());
        var uniqueStopWords = new HashSet<>(stopWords(words));
        return new HashSet<>(words).size() - uniqueStopWords.size();
    }

    private ArrayList<String> separateWordsByWhitespaces() {
        var words = new ArrayList<String>();
        try {
            if (filePath.isEmpty()) {
                text = text.replaceAll("[.]", " ");
                var wordCandidates = text.split(" ");
                words.addAll(filterWordsContainingAlphabeticCharsOnly(wordCandidates));
            } else {
                var file = new File(filePath);
                try (var scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        var fileContent = scanner.nextLine();
                        var wordCandidates = fileContent.split(" ");
                        words.addAll(filterWordsContainingAlphabeticCharsOnly(wordCandidates));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("The file %s which contains text to be counted was not found.",
                    Path.of(filePath).getFileName()), e);
        }
        return words;
    }

    private ArrayList<String> filterWordsContainingAlphabeticCharsOnly(String[] wordCandidates) {
        var words = new ArrayList<String>();
        for (var wordCandidate : wordCandidates) {
            var hasLettersOnly = true;
            for (char c : wordCandidate.toCharArray()) {
                if (Character.isAlphabetic(c) || c=='-') {
                    continue;
                }
                hasLettersOnly = false;
            }
            if (wordCandidate.equals("")) {
                continue;
            }
            if (hasLettersOnly) {
                words.add(wordCandidate);
            }
        }
        return words;
    }

    private ArrayList<String> stopWords(ArrayList<String> words) {
        var stopWords = new ArrayList<String>();
        try {
            var stopWordsFile = new File(STOP_WORDS_PATH);
            try (var scanner = new Scanner(stopWordsFile)) {
                while (scanner.hasNextLine()) {
                    var fileContent = scanner.nextLine();
                    for (var stopWord : fileContent.split(" ")) {
                        for (var word : words) {
                            if (word.equals(stopWord)) {
                                stopWords.add(word);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException(String.format("The file %s which contains predefined stop words, that are not counted, was not found.%n",
                    Path.of(STOP_WORDS_PATH).getFileName()), e);
        }
        return stopWords;
    }
}
