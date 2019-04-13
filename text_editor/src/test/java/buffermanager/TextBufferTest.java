package buffermanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TextBufferTest {

    TextBuffer textBuffer;

    @BeforeEach
    void setup() {
        textBuffer = new TextBuffer("hello");
    }

    @AfterEach
    void clean(){
        textBuffer= null;
    }

    @Test
    void basicTest() {
        assertEquals("hello", textBuffer.toString());
    }

    @Test
    void appendTest() {
        textBuffer.append("world");
        assertEquals("helloworld", textBuffer.toString());
    }

    @Test
    void insertTest() {
        textBuffer.insert(3,"world");
        assertEquals("helworldlo",textBuffer.toString());
    }

    @Test
    void undoTest(){
        textBuffer.append("world");
        textBuffer.undo();
        assertEquals("hello",textBuffer.toString());
    }

    @Test
    void eraseTest(){
        textBuffer.append(" ");
        textBuffer.undo();
        textBuffer.append("everyone");
        textBuffer.erase(3,10);
        assertEquals("helloevery",textBuffer.toString());
    }

    @Test
    void eraseTrailingTest(){
        textBuffer.eraseTrailing(2);
        assertEquals("hel",textBuffer.toString());
    }

    @Test
    void redoTest(){
        textBuffer.append(" ");
        textBuffer.append("everyone");
        textBuffer.append(" on this planet");
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.redo();
        assertEquals("hello everyone",textBuffer.toString());
    }

    @Test
    void exceptionTest(){
        assertThrows(StringIndexOutOfBoundsException.class, () ->
                textBuffer.erase(2,7));
    }
}
