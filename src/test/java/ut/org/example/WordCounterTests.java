package ut.org.example;

import org.example.WordCounter;
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
        WordCounter sut = new WordCounter();
        int wordCount = sut.countWords(words);
        assertEquals(expected, wordCount, "word count");
    }

    @Test
    void countsOneWordSurroundedByWhiteSpaces() {
        WordCounter sut = new WordCounter();
        int wordCount = sut.countWords("   word   ");
        assertEquals(1, wordCount, "word count");
    }

    @Test
    void countsTwoWordsSeparatedByMultipleWhiteSpaces() {
        WordCounter sut = new WordCounter();
        int wordCount = sut.countWords("word        word");
        assertEquals(2, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"wo3rd", "wo$rd"})
    void doesNotCountWordCandidatesContainingANonLatinAlphabetCharacter(String wordCandidate) {
        WordCounter sut = new WordCounter();
        int wordCount = sut.countWords(wordCandidate);
        assertEquals(0, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"word.", "word,", "word!", "word?"})
    void doesNotCountWordCandidatesContainingAPunctuationCharacter(String wordCandidate) {
        WordCounter sut = new WordCounter();
        int wordCount = sut.countWords(wordCandidate);
        assertEquals(0, wordCount, "word count");
    }

    @ParameterizedTest
    @ValueSource(strings = {"on", "the", "off", "a"})
    void doesNotCountStopWords(String wordCandidate) {
        WordCounter sut = new WordCounter();
        int wordCount = sut.countWords(wordCandidate);
        assertEquals(0, wordCount, "word count");
    }

    @Test
    void doesNotCountWordCandidateContainingAHyphenCharacter() {
        WordCounter sut = new WordCounter();
        int wordCount = sut.countWords("word-word");
        assertEquals(0, wordCount, "word count");
    }

    @Test
    void inputTextMustNotBeNull() {
        WordCounter sut = new WordCounter();
        Exception exception = assertThrows(NullPointerException.class, () -> sut.countWords((String) null));
        assertEquals("input text must not be null", exception.getMessage());
    }
}
