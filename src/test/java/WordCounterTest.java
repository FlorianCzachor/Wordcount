import org.example.WordCounter;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterTest {

    @Test
    void UserInputContainsStopWord() {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word a",null));
    }
    @Test
    void usingMyTextFile() {
        var wordCount = new WordCounter();
        assertEquals(4,wordCount.count("","/Users/florianczachor/Documents/GitHub/Wordcount/mytext.txt"));
    }
    @Test
    void usingUserInput() {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word",null));
    }
    @Test
    void userInputContainsStopWord() {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word a",null));
    }
    @Test
    void userInputContainsNonAlphabetic() {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word word word 1",null));
    }
    @Test
    void userInputContainsLotsOfSpaces() {
        var wordCount = new WordCounter();
        assertEquals(3,wordCount.count("word   word   word",null));
    }
}