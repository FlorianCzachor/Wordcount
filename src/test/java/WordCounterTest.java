import org.example.WordCounter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterTest {

    @Test
    void UserInputContainsStopWord() throws FileNotFoundException {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word a",null));
    }
    @Test
    void usingMyTextFile() throws FileNotFoundException {
        var wordCount = new WordCounter();
        assertEquals(4,wordCount.count("","/Users/florianczachor/Documents/GitHub/Wordcount/mytext.txt"));
    }
    @Test
    void usingUserInput() throws FileNotFoundException {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word",null));
    }
    @Test
    void userInputContainsStopWord() throws FileNotFoundException {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word a",null));
    }
    @Test
    void userInputContainsNonAlphabetic() throws FileNotFoundException {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word 1",null));
    }
    @Test
    void userInputContainsLotsOfSpaces() throws FileNotFoundException {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word   word   word",null));
    }
}