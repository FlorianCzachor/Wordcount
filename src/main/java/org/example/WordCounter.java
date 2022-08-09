package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class WordCounter {

    private static final String STOP_WORDS_FILE_PATH = "src/main/resources/stopwords.txt";

    /**
     * Counts words in text of input file. Please see {@link WordCounter#countWords(String)} to learn more about what
     * kind of words are counted since this method is reused internally to count words. This method uses
     * {@link Files#readString(Path)} internally to read the file's content.
     * <br /><br />
     *
     * @param inputFile containing text of words which will be counted; must not be null
     * @return word count of words in text of file
     * @throws NullPointerException if inputText is null
     * @throws RuntimeException     when any {@link IOException} is thrown by the internally used
     *                              {@link Files#readString(Path)} method. For example if an I/O error occurs when
     *                              reading from the file.
     * @see WordCounter#countWords(String)
     */
    public int countWords(Path inputFile) {
        Objects.requireNonNull(inputFile, "input file path must not be null");

        try {
            var inputText = Files.readString(inputFile);
            return countWords(inputText);
        } catch (IOException e) {
            throw new RuntimeException(format("Can't find input file: ' %s", inputFile.getFileName()), e);
        }
    }

    /**
     * Counts words in text which are separated by white-space(s) and only contains letters from the latin alphabet
     * (A-Z,a-Z). Otherwise, words are not counted.
     * <p>
     * Stop words are also not counted. Stop words are stored in a file named stopwords.txt which is bundled with the
     * application and loaded from classpath. Stop words are: "on", "the", "off", "a" which are defined in the
     * requirements. If the stop words file can't be loaded, stop words are ignored.
     * <br /><br />
     * Examples:
     * <pre>
     *    inputText => word count
     *       "word" => 1
     *  "word word" => 2
     * "word wo3rd" => 1 // "3" not in A-Z or a-z
     * "word, word" => 1 // "," not in A-Z or a-z
     *    "word on" => 1 // "on" is a stop word
     * </pre>
     * @param inputText containing words which will be counted; must not be null
     * @return word count of words which are separted by white-space(s) and only contain letters from the latin alphabet
     * and are not stop words.
     * @throws NullPointerException if inputText is null
     */
    public int countWords(String inputText) {
        Objects.requireNonNull(inputText, "input text must not be null");

        var stopWords = getStopWords();
        var words = parseWords(inputText.split("[ \n]"));
        return (int) words.stream()
            .filter(w -> !stopWords.contains(w))
            .count();
    }

    private List<String> getStopWords() {
        try {
            return Files.readAllLines(Paths.get(STOP_WORDS_FILE_PATH))
                .stream()
                .map(l -> asList(l.split(" ")))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

    private List<String> parseWords(String[] inputWords) {
        return Arrays.stream(inputWords)
            .filter(s -> !s.isEmpty())
            .filter(word -> word.chars().allMatch(Character::isLetter))
            .collect(Collectors.toList());
    }

}
