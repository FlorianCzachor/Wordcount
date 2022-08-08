package ut.org.example;

import org.example.Wordcount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordCounterTests {

    private static final String IGNORE_FILE_NAME = "";

    @ParameterizedTest
    @CsvSource({
        "word, 1",
        "word word, 2",
        "word word word, 3",
    })
    void countsWords(String words, int expected) throws FileNotFoundException {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(words, IGNORE_FILE_NAME);
        assertEquals(expected, wordCount, "word count");
    }

    @Test
    void countsOneWordSurroundedByWhiteSpaces() throws FileNotFoundException {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count("   word   ", IGNORE_FILE_NAME);
        assertEquals(1, wordCount, "word count");
    }

    @Test
    void countsTwoWordsSeparatedByMultipleWhiteSpaces() throws FileNotFoundException {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count("word        word", IGNORE_FILE_NAME);
        assertEquals(2, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"wo3rd", "wo$rd"})
    void doesNotCountWordCandidatesContainingANonLatinAlphabetCharacter(String wordCandidate) throws FileNotFoundException {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(wordCandidate, IGNORE_FILE_NAME);
        assertEquals(0, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"word.", "word,", "word!", "word?"})
    void doesNotCountWordCandidatesContainingAPunctuationCharacter(String wordCandidate) throws FileNotFoundException {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(wordCandidate, IGNORE_FILE_NAME);
        assertEquals(0, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"on", "the", "off", "a"})
    void doesNotCountStopWords(String wordCandidate) throws FileNotFoundException {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(wordCandidate, IGNORE_FILE_NAME);
        assertEquals(0, wordCount, "word count");
    }

    @Test
    void doesNotCountWordCandidateContainingAHyphenCharacter() throws FileNotFoundException {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count("word-word", IGNORE_FILE_NAME);
        assertEquals(0, wordCount, "word count");
    }

    @Test
    void userInputMustNotBeNull() {
        Wordcount sut = new Wordcount();
        Exception exception = assertThrows(NullPointerException.class, () -> sut.count(null, IGNORE_FILE_NAME));
        assertEquals("user input must not be null", exception.getMessage());
    }
}
