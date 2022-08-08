package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class WordCounter {

    public int count(Path userInputFile) {
        Objects.requireNonNull(userInputFile, "file path must not be null");

        List<String> stopWords = getStopWords();
        try {
            List<String> words = new ArrayList<>();
            for (String l : Files.readAllLines(userInputFile)) {
                words.addAll(parseWords(l.split(" ")));
            }
            return (int) words.stream()
                .filter(w -> !stopWords.contains(w))
                .count();
        } catch (IOException e) {
            throw new RuntimeException(format("Can't find user input file: ' %s", userInputFile.getFileName()), e);
        }
    }

    public int count(String input) {
        Objects.requireNonNull(input, "user input must not be null");

        List<String> stopWords = getStopWords();
        List<String> words = parseWords(input.split(" "));
        return (int) words.stream()
            .filter(w -> !stopWords.contains(w))
            .count();
    }

    private List<String> parseWords(String[] inputWords) {
        return Arrays.stream(inputWords)
            .filter(s -> !s.isEmpty())
            .filter(word -> word.chars().allMatch(Character::isLetter))
            .collect(Collectors.toList());
    }

    private List<String> getStopWords() {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/stopwords.txt"))
                .stream()
                .map(l -> asList(l.split(" ")))
                .flatMap(List::stream)
                .collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.emptyList();
        }
    }

}