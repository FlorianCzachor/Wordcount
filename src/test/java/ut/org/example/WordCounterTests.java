package ut.org.example;

import org.example.Wordcount;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordCounterTests {

    @ParameterizedTest
    @CsvSource({
        "word, 1",
        "word word, 2",
        "word word word, 3",
    })
    void countsWords(String words, int expected) {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(words);
        assertEquals(expected, wordCount, "word count");
    }

    @Test
    void countsOneWordSurroundedByWhiteSpaces() {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count("   word   ");
        assertEquals(1, wordCount, "word count");
    }

    @Test
    void countsTwoWordsSeparatedByMultipleWhiteSpaces() {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count("word        word");
        assertEquals(2, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"wo3rd", "wo$rd"})
    void doesNotCountWordCandidatesContainingANonLatinAlphabetCharacter(String wordCandidate) {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(wordCandidate);
        assertEquals(0, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"word.", "word,", "word!", "word?"})
    void doesNotCountWordCandidatesContainingAPunctuationCharacter(String wordCandidate) {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(wordCandidate);
        assertEquals(0, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"on", "the", "off", "a"})
    void doesNotCountStopWords(String wordCandidate) {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count(wordCandidate);
        assertEquals(0, wordCount, "word count");
    }

    @Test
    void doesNotCountWordCandidateContainingAHyphenCharacter() {
        Wordcount sut = new Wordcount();
        int wordCount = sut.count("word-word");
        assertEquals(0, wordCount, "word count");
    }

    @Test
    void userInputMustNotBeNull() {
        Wordcount sut = new Wordcount();
        Exception exception = assertThrows(NullPointerException.class, () -> sut.count((String) null));
        assertEquals("user input must not be null", exception.getMessage());
    }
}
