package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.Arrays.asList;

public class Wordcount {

    public int count(Path path) {
        Objects.requireNonNull(path, "file path must not be null");

        List<String> words = new ArrayList<>();
        int stopWordOccurrences = 0;
        try {
            List<String> lines = Files.readAllLines(path);
            for (String l : lines) {
                String[] inputWords = l.split(" ");
                words.addAll(checkWords(inputWords));
                stopWordOccurrences = countStopWordOccurrencesIn(words);
            }
        } catch (IOException e) {
            throw new RuntimeException(format("Can't find user input file: ' %s", path.getFileName()), e);
        }
        return words.size() - stopWordOccurrences;
    }

    public int count(String input) {
        Objects.requireNonNull(input, "user input must not be null");

        String[] inputWords = input.split(" ");
        List<String> words = checkWords(inputWords);
        int stopWordOccurrences = countStopWordOccurrencesIn(words);
        return words.size() - stopWordOccurrences;
    }

    private List<String> checkWords(String[] inputWords) {
        return Arrays.stream(inputWords)
            .filter(s -> !s.isEmpty())
            .filter(word -> word.chars().allMatch(Character::isLetter))
            .collect(Collectors.toList());
    }

    private int countStopWordOccurrencesIn(List<String> words) {
        try {
            List<String> stopWords = Files.readAllLines(Paths.get("src/main/resources/stopwords.txt"))
                .stream()
                .map(l -> asList(l.split(" ")))
                .flatMap(List::stream)
                .collect(Collectors.toList());

            return (int) words.stream().filter(stopWords::contains).count();
        } catch (IOException e) {
            return 0;
        }
    }

}
