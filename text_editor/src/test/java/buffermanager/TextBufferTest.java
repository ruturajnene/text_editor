package buffermanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The type Text buffer test.
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TextBufferTest {


    /**
     * The Text buffer.
     */
    TextBuffer textBuffer;
    private String HELLO = "hello";
    private String WORLD = "world";

    /**
     * Sets .
     */
    @BeforeEach
    void setup() {
        textBuffer = new TextBuffer(HELLO);
    }

    /**
     * Clean.
     */
    @AfterEach
    void clean() {
        textBuffer = null;
    }

    /**
     * Basic test.
     */
    @Test
    void basicTest() {
        assertEquals(HELLO, textBuffer.toString());
    }

    /**
     * Append test.
     */
    @Test
    void appendTest() {
        textBuffer.append(WORLD);
        assertEquals("helloworld", textBuffer.toString());
    }

    /**
     * Insert test.
     */
    @Test
    void insertTest() {
        textBuffer.insert(3, WORLD);
        assertEquals("helworldlo", textBuffer.toString());
    }

    /**
     * Undo test.
     */
    @Test
    void undoTest() {
        textBuffer.append(WORLD);
        textBuffer.undo();
        assertEquals(HELLO, textBuffer.toString());
    }

    /**
     * Erase test.
     */
    @Test
    void eraseTest() {
        textBuffer.append(" ");
        textBuffer.undo();
        textBuffer.append("everyone");
        textBuffer.erase(10, 3);
        assertEquals("helloevery", textBuffer.toString());
    }

    /**
     * Erase trailing test.
     */
    @Test
    void eraseTrailingTest() {
        textBuffer.eraseTrailing(2);
        assertEquals("hel", textBuffer.toString());
    }

    /**
     * Limited buffer size test.
     */
    @Test
    void limitedBufferSizeTest() {
        textBuffer.replace(HELLO, "Bye");
        textBuffer.append(" ");
        textBuffer.append("everyone");
        textBuffer.append(" on this planet");
        textBuffer.append(".");
        textBuffer.eraseTrailing(4);
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        assertEquals("Bye", textBuffer.toString());
    }

    /**
     * Redo test.
     */
    @Test
    void redoTest() {
        textBuffer.append(WORLD);
        textBuffer.undo();
        textBuffer.redo();
        assertEquals("helloworld", textBuffer.toString());
    }

    /**
     * Load file test.
     */
    @Test
    void loadFileTest() {
        textBuffer.append(". ");
        textBuffer.loadFile("src/test/testfile.txt");
        assertEquals("Hi, this is a test file.", textBuffer.toString());
    }

    /**
     * Save file test.
     */
    @Test
    void saveFileTest() {
        textBuffer.append(". This is a test to save a file");
        textBuffer.saveFile("src/test/savefile.txt");
        File file = new File("src/test/savefile.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String string;
            while ((string = br.readLine()) != null) {
                builder.append(string);
            }
            assertEquals(builder.toString(), textBuffer.toString());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    void insertBoundaryTest() {
        textBuffer.insert(9, " everyone");
        assertEquals(HELLO, textBuffer.toString());
    }

    @Test
    void eraseExceptionTest() {
        textBuffer.erase(5, Integer.MAX_VALUE);
        assertEquals(HELLO, textBuffer.toString());
    }

    @Test
    void redoExceptionTest() {
        textBuffer.redo();
        assertEquals(HELLO, textBuffer.toString());
    }

    @Test
    void eraseTrailingExceptionTest() {
        textBuffer.eraseTrailing(6);
        assertEquals(HELLO, textBuffer.toString());
    }

    @Test
    void replaceMultipleTest() {
        textBuffer.append(" ");
        textBuffer.append(HELLO);
        textBuffer.replace(HELLO, "bye");
        assertEquals("bye bye", textBuffer.toString());
    }

    @Test
    void replaceTest(){
        textBuffer.replace(HELLO,"Bye");
        textBuffer.append(" World");
        textBuffer.undo();
        textBuffer.undo();
        assertEquals(HELLO, textBuffer.toString());
    }
}
