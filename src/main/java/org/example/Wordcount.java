package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Wordcount {

    private static final int ASCII_CHAR_UPPER_CASE_A = 65;
    private static final int ASCII_CHAR_UPPER_CASE_Z = 90;
    private static final int ASCII_CHAR_LOWER_CASE_A = 97;
    private static final int ASCII_CHAR_LOWER_CASE_Z = 122;

    public int count(String input, String filename) throws FileNotFoundException {
        File f = new File("src/main/resources/" + filename);
        List<String> result = new ArrayList<>();
        int stopwords = 0;

        if (f.exists() && f.isFile()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String inputWords[] = data.split(" ");
                List<String> temp = checkWords(inputWords);
                result.addAll(temp);
                stopwords = stopWords(result);
            }
            myReader.close();
        } else {
            String inputWords[] = input.split(" ");
            result = checkWords(inputWords);
            stopwords = stopWords(result);
        }
        return result.size() - stopwords;
    }

    private List<String> checkWords(String[] inputWords) {
        List<String> fileContent = new ArrayList<>();
        for (String word : inputWords) {
            boolean onlyLetters = true;
            for (int i = 0; i < word.length(); i++) {
                if (containsSymbol(word.charAt(i))) {
                    onlyLetters = false;
                }
            }
            if (word.equals("")) {
                continue;
            }
            if (onlyLetters) {
                fileContent.add(word);
            }
        }
        return fileContent;
    }

    private int stopWords(List<String> result) throws FileNotFoundException {
        File f = new File("src/main/resources/stopwords.txt");
        int stopwords = 0;

        if (f.exists() && !f.isDirectory()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String inputWords[] = data.split(" ");
                for (int i = 0; i < inputWords.length; i++) {
                    for (int j = 0; j < result.size(); j++) {
                        if (result.get(j).equals(inputWords[i])) {
                            stopwords++;
                        }
                    }
                }
            }
            myReader.close();
        }
        return stopwords;
    }

    public boolean containsSymbol(int charValue) {
        return (charValue < ASCII_CHAR_UPPER_CASE_A)
                || ((charValue > ASCII_CHAR_UPPER_CASE_Z) && (charValue < ASCII_CHAR_LOWER_CASE_A))
                || (charValue > ASCII_CHAR_LOWER_CASE_Z);
    }
}
