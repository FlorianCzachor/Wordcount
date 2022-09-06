package org.example;
import java.nio.file.Path;
import java.util.*;
import java.io.*;

/**
 * A word counter can count the number of words as well as the number of unique words.
 * To know more about which words exactly are counted see {@link WordCounter#countWords()}
 * <p>
 * <ul>
 * <li> Words can either be counted from a .txt file, or from user console input.
 * <li> The stop words file contains words, that should be excluded from the counting.
 * <li> Only alphabetic words, words containing with a '-' or a '.' are counted.
 * <li> The count is never negative.
 * </ul>
 */
public class WordCounter {
    private static final String STOP_WORDS_PATH = "src/main/resources/stopwords.txt";
    private String text;
    private final String filePath;
    private ArrayList<String> words;
    private final HashSet<String> uniqueStopWords = new HashSet<>();

    public WordCounter(String text, String filePath) {
        Objects.requireNonNull(text, "Text must not be null");
        Objects.requireNonNull(filePath, "FilePath must not be null");

        this.text = text;
        this.filePath = filePath;
    }

    /**
     * Counts number of words from .txt file which you need to set in the constructor, for more information {@link WordCounter#WordCounter(String text, String filePath)} or console user input.
     * They consist of alphabetic letters from (A-Z, a-z) (=> see {@link WordCounter#filterWordsContainingAlphabeticCharsOnly(String[])}) and are separated by white-space(s).
     * Other words are not counted.
     * <p>
     * Predefined stop words are excluded. Stop words which are defined in the requirements and are stored in a text file named "stopwords.txt".
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
     * @return number of words containing alphabetic letters that are separated by white-space(s)
     * and are not stop words
     * @throws NullPointerException if userInput or myTextFilePath is null
     * @see WordCounter#filterWordsContainingAlphabeticCharsOnly(String[]) check if words are alphabetic letters from (A-Z, a-z)
     * @see WordCounter#countUniqueWords() there is also a method for counting unique words
     */
    public int countWords() {
        separateWords();
        return words.size() - stopWords();
    }

    /**
     * Counts number of unique words from already filtered words Arraylist.
     * These words get passed through a HashSet, so that only one of every word exists.
     * <p>
     * The size of that list is subtracted by the number of unique stop words.
     * You can find more information on stop words here: {@link WordCounter#countWords()}
     * <p>
     * Example:
     * <pre>
     *    inputText => word count {@link WordCounter#countWords()} + unique word count
     *              "hello" => 1 + 1
     *        "hello hello" => 2 + 1
     *        "hello world" => 2 + 2
     * "hello on the world" => 2 + 2 // "on" and "the" are stop words
     * </pre>
     *
     * @return number of valid alphabetic words subtracted by the number of unique stop words
     * @see WordCounter#countWords()
     */
    public int countUniqueWords() {
        separateWords();

        var uniqueWords = new HashSet<>(words);
        return uniqueWords.size() - uniqueStopWords.size();
    }

    private void separateWords() {
        words = new ArrayList<>();
        try {
            if (filePath.isEmpty()) {
                text = text.replaceAll("[-.]", " ");
                var wordCandidates = text.split(" ");
                words = filterWordsContainingAlphabeticCharsOnly(wordCandidates);
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
    }

    private ArrayList<String> filterWordsContainingAlphabeticCharsOnly(String[] wordCandidates) {
        var words = new ArrayList<String>();
        for (var wordCandidate : wordCandidates) {
            var hasLettersOnly = true;
            for (char c : wordCandidate.toCharArray()) {
                if (Character.isAlphabetic(c)) {
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

    private int stopWords() {
        var stopWordsFile = new File(STOP_WORDS_PATH);
        var stopWordCount = 0;

        try {
            var scanner = new Scanner(stopWordsFile);
            while (scanner.hasNextLine()) {
                var fileContent = scanner.nextLine();
                var stopWords = fileContent.split(" ");
                for (var stopWord : stopWords) {
                    for (var word : words) {
                        if (word.equals(stopWord)) {
                            stopWordCount++;
                            uniqueStopWords.add(word);
                        }
                    }
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.printf("The file %s which contains predefined stop words, that are not counted, was not found.%n",
                    Path.of(STOP_WORDS_PATH).getFileName());
        }
        return stopWordCount;
    }
}
