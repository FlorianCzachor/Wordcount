package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Wordcount {

    public int count(String input, String filename) throws FileNotFoundException {
        Objects.requireNonNull(input, "user input must not be null");

        File f = new File("src/main/resources/" + filename);
        List<String> words = new ArrayList<>();
        int stopWordOccurrences = 0;

        if (f.exists() && f.isFile()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] inputWords = data.split(" ");
                List<String> temp = checkWords(inputWords);
                words.addAll(temp);
                stopWordOccurrences = countStopWordOccurrencesIn(words);
            }
            myReader.close();
        } else {
            String[] inputWords = input.split(" ");
            words = checkWords(inputWords);
            stopWordOccurrences = countStopWordOccurrencesIn(words);
        }
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
            List<String> stopWords = Files.lines(Paths.get("src/main/resources/stopwords.txt"))
                .map(l -> asList(l.split(" ")))
                .flatMap(List::stream)
                .collect(Collectors.toList());

            return (int) words.stream().filter(stopWords::contains).count();
        } catch (IOException e) {
            return 0;
        }
    }

}
