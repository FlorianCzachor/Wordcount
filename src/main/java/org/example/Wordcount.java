package org.example;
import java.util.*;
import java.io.*;
public class Wordcount {
    public int count(String input, String filename) throws FileNotFoundException {
        File f = new File("src/main/resources/" + filename);
        List<String> result = new ArrayList<>();
        int stopwords = 0;

        if (f.exists() && !f.isDirectory()) {
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
            for (int i=0; i<word.length(); i++) {
                if (containsSymbol(word.charAt(i))) {
                    onlyLetters = false;
                }
            }
            if (!word.equals("") && onlyLetters) {
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

    public boolean containsSymbol(int charValue) {
        // ASCII Char Values
        final int MIN_UPPERCASE_CHAR_VALUE = 65;
        final int MAX_UPPERCASE_CHAR_VALUE = 90;

        final int MIN_LOWERCASE_CHAR_VALUE = 97;
        final int MAX_LOWERCASE_CHAR_VALUE = 122;
        return charValue < MIN_UPPERCASE_CHAR_VALUE || charValue > MAX_UPPERCASE_CHAR_VALUE && charValue < MIN_LOWERCASE_CHAR_VALUE || charValue > MAX_LOWERCASE_CHAR_VALUE;
    }
}
