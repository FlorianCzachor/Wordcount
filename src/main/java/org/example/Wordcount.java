package org.example;
import java.util.*;
import java.io.*;
public class Wordcount {
    public int count(String input, String path) throws FileNotFoundException {
        List<String> result = new ArrayList<>();
        int stopwords = 0;

        // checks if path is empty then uses user input otherwise use the mytext.txt file
        if (path == null) {
            String inputWords[] = input.split(" ");
            result = checkWords(inputWords);
            stopwords = stopWords(result);
        } else {
            File f = new File(path);
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String inputWords[] = data.split(" ");
                List<String> temp = checkWords(inputWords);
                result.addAll(temp);
            }
            myReader.close();
            stopwords = stopWords(result);
        }
        return result.size() - stopwords;
    }

    // Method checkWords checks if the words only constist of letters and returns a list of valid words
    private List<String> checkWords(String[] inputWords) {
        List<String> fileContent = new ArrayList<>();
        for (String word : inputWords) {
            boolean onlyLetters = true;
            for (int i=0; i<word.length(); i++) {
                if (containsSymbol(word.charAt(i))) {
                    onlyLetters = false;
                }
            }
            if (onlyLetters && !word.equals("")) {
                fileContent.add(word);
            }
        }
        return fileContent;
    }

    // Method containsSymbol checks if the char is outside of Rage: A-Z and a-z
    public boolean containsSymbol(int charValue) {
        // ASCII Char Values
        final int MIN_UPPERCASE_CHAR_VALUE = 65;
        final int MAX_UPPERCASE_CHAR_VALUE = 90;

        final int MIN_LOWERCASE_CHAR_VALUE = 97;
        final int MAX_LOWERCASE_CHAR_VALUE = 122;
        return charValue < MIN_UPPERCASE_CHAR_VALUE || charValue > MAX_UPPERCASE_CHAR_VALUE && charValue < MIN_LOWERCASE_CHAR_VALUE || charValue > MAX_LOWERCASE_CHAR_VALUE;
    }

    // Method stopWords counts how many times a word of stopwords.txt is inside ArrayList result
    private int stopWords(List<String> result) throws FileNotFoundException {
        File f = new File("src/main/resources/stopwords.txt");
        int stopwords = 0;

        if (f.exists() && !f.isDirectory()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String inputWords[] = data.split(" ");
                for (int i=0; i<inputWords.length; i++) {
                    for (int j=0; j<result.size(); j++) {
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
}
