package org.example;
import java.util.*;
import java.io.*;
public class Wordcount {
    public int count(String input) throws FileNotFoundException {
        File f = new File("src/main/resources/" + input);
        List<String> result = new ArrayList<>();

        // Reading stopwords.txt file and adding words into ArrayList fileContent
        if (input.contains(".txt") && f.exists() && !f.isDirectory()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String inputWords[] = data.split(" ");
                result = checkWords(inputWords);
            }
            myReader.close();
        } else {
            Scanner s = new Scanner(System.in);
            System.out.print("Please enter a text: ");
            input = s.nextLine();
            String inputWords[] = input.split(" ");
            result = checkWords(inputWords);
        }
        return result.size();
    }

    private List<String> checkWords(String[] inputWords) {
        List<String> fileContent = new ArrayList<>();
        for (String word : inputWords) {
            boolean onlyLetters = true;
            for (int i=0; i<word.length(); i++) {
                if (!isLetter(word.charAt(i))) {
                    onlyLetters = false;
                }
            }
            if (!word.equals("") && onlyLetters) {
                fileContent.add(word);
            }
        }
        return fileContent;
    }

    public boolean isLetter(int charValue) {
        // ASCII Char Values
        final int MIN_UPPERCASE_CHAR_VALUE = 65;
        final int MAX_UPPERCASE_CHAR_VALUE = 90;

        final int MIN_LOWERCASE_CHAR_VALUE = 97;
        final int MAX_LOWERCASE_CHAR_VALUE = 122;
        return charValue >= MIN_UPPERCASE_CHAR_VALUE && charValue <= MAX_UPPERCASE_CHAR_VALUE || charValue >= MIN_LOWERCASE_CHAR_VALUE && charValue <= MAX_LOWERCASE_CHAR_VALUE;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner s = new Scanner(System.in);
        System.out.print("Please enter the filename.txt: ");
        String input = s.nextLine();
        Wordcount w = new Wordcount();
        int i = w.count(input);
        System.out.println("Number of words: " + i);
    }
}
