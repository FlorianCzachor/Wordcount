package org.example;
import java.util.*;
import java.io.*;
public class Wordcount {
    public int count(String input) {
        int words = 0;
        List<String> fileContent = new ArrayList<String>();

        // Reading stopwords.txt file and adding words into ArrayList fileContent
        try {
            File f = new File("src/main/resources/stopwords.txt");
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fileContent.add(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Checking if the input contains any words of stopwords.txt
        String inputWords[] = input.split(" ");
        for (int i=0; i<inputWords.length; i++) {
            for (int j=0; j<fileContent.size(); j++) {
                if (fileContent.get(j).equals(inputWords[i]))
                    words--;
            }
        }

        // Return number of words - number of stopwords
        return inputWords.length + words;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter text: ");
        String input = s.nextLine();
        Wordcount w = new Wordcount();
        int i = w.count(input);
        System.out.println("Number of words: " + i);
    }
}
