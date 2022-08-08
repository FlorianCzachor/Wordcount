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

    public int countWords(Path userInputFile) {
        Objects.requireNonNull(userInputFile, "user input file path must not be null");

        try {
            String inputText = Files.readString(userInputFile);
            return countWords(inputText);
        } catch (IOException e) {
            throw new RuntimeException(format("Can't find user input file: ' %s", userInputFile.getFileName()), e);
        }
    }

    public int countWords(String inputText) {
        Objects.requireNonNull(inputText, "input text must not be null");

        List<String> stopWords = getStopWords();
        List<String> words = parseWords(inputText.split("[ \n]"));
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
