package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Wordcount {

    public int count(String input, String filename) throws FileNotFoundException {
        File f = new File("src/main/resources/" + filename);
        List<String> result = new ArrayList<>();
        int stopwords = 0;

        if (f.exists() && f.isFile()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] inputWords = data.split(" ");
                List<String> temp = checkWords(inputWords);
                result.addAll(temp);
                stopwords = stopWords(result);
            }
            myReader.close();
        } else {
            String[] inputWords = input.split(" ");
            result = checkWords(inputWords);
            stopwords = stopWords(result);
        }
        return result.size() - stopwords;
    }

    private List<String> checkWords(String[] inputWords) {
        return Arrays.stream(inputWords)
            .filter(s -> !s.isEmpty())
            .filter(word -> word.chars().allMatch(Character::isLetter))
            .collect(Collectors.toList());
    }

    private int stopWords(List<String> result) throws FileNotFoundException {
        File f = new File("src/main/resources/stopwords.txt");
        int stopwords = 0;

        if (f.exists() && f.isFile()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] inputWords = data.split(" ");
                for (String inputWord : inputWords) {
                    for (String s : result) {
                        if (s.equals(inputWord)) {
                            stopwords++;
                        }
                    }
                }
            }
            myReader.close();
        }
        return stopwords;
    }

}
