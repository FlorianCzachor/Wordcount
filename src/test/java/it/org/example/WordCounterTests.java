package it.org.example;

import org.example.Main;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordCounterTests {

    private static final String[] NO_ARGS = new String[0];
    private static final String TEST_ERROR_MESSAGE_FORMAT = "Application output must contain: '%s' instead of '%s'";
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void countsWordsFromCommandLineUserInput() throws FileNotFoundException {
        testIn = new ByteArrayInputStream("Mary had a little lamb".getBytes());
        System.setIn(testIn);

        Main.main(NO_ARGS);

        String actual = testOut.toString();
        String expected = "Number of words: 4";
        assertTrue(actual.contains(expected), format(TEST_ERROR_MESSAGE_FORMAT, expected, actual));
    }

    @Test
    void countsWordsFromFileUserInput() throws FileNotFoundException {
        Main.main(new String[]{"input.txt"});

        String actual = testOut.toString();
        String expected = "Number of words: 4";
        assertTrue(actual.contains(expected), format(TEST_ERROR_MESSAGE_FORMAT, expected, actual));
    }

}
