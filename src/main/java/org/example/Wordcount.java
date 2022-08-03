package org.example;
import java.util.*;
import java.io.*;
public class Wordcount {
    public int count(String input) throws FileNotFoundException {
        int words = 0;
        List<String> fileContent = new ArrayList<String>();

        File f = new File("src/main/resources/" + input);
        // Reading stopwords.txt file and adding words into ArrayList fileContent
        if (input.contains(".txt") && f.exists() && !f.isDirectory()) {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String inputWords[] = data.split(" ");
                for (int i=0; i<inputWords.length; i++) {
                    fileContent.add(inputWords[i]);
                }
            }
            myReader.close();
            words = fileContent.size();
        } else {
            Scanner s = new Scanner(System.in);
            System.out.print("Please enter a text: ");
            input = s.nextLine();
            String inputWords[] = input.split(" ");
            words = inputWords.length;
        }

        // Checking if the input contains any words of stopwords.txt
        /*
        String inputWords[] = input.split(" ");
        for (int i=0; i<inputWords.length; i++) {
            for (int j=0; j<fileContent.size(); j++) {
                if (fileContent.get(j).equals(inputWords[i]))
                    words--;
            }
        }
         */

        // Return number of words - number of stopwords
        return words;
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
