import org.example.WordCounter;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WordCounterTest {

    @Test
    // @ParameterizedTest
    // @Variable
    void UserInputContainsStopWord() {
        var wordCount = new WordCounter("word word word a","");
        assertEquals(3,wordCount.countWords());
    }
    @Test
    void usingMyTextFile() {
        var wordCount = new WordCounter("","/Users/florianczachor/Documents/GitHub/Wordcount/mytext.txt");
        assertEquals(4,wordCount.countWords());
    }
    @Test
    void usingUserInput() {
        var wordCount = new WordCounter("word word word","");
        assertEquals(3,wordCount.countWords());
    }
    @Test
    void userInputContainsStopWord() {
        var wordCount = new WordCounter("word word word a","");
        assertEquals(3,wordCount.countWords());
    }
    @Test
    void userInputContainsNonAlphabetic() {
        var wordCount = new WordCounter("word word word 1","");
        assertEquals(3,wordCount.countWords());
    }
    @Test
    void userInputContainsLotsOfSpaces() {
        var wordCount = new WordCounter("word   word   word","");
        assertEquals(3,wordCount.countWords());
    }
}