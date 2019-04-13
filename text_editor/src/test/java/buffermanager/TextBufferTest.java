package buffermanager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TextBufferTest {


    TextBuffer textBuffer;
    private String HELLO= "hello";
    private String WORLD= "world";

    @BeforeEach
    void setup() {
        textBuffer = new TextBuffer(HELLO);
    }

    @AfterEach
    void clean(){
        textBuffer= null;
    }

    @Test
    void basicTest() {
        assertEquals(HELLO, textBuffer.toString());
    }

    @Test
    void appendTest() {
        textBuffer.append(WORLD);
        assertEquals("helloworld", textBuffer.toString());
    }

    @Test
    void insertTest() {
        textBuffer.insert(3,WORLD);
        assertEquals("helworldlo",textBuffer.toString());
    }

    @Test
    void undoTest(){
        textBuffer.append(WORLD);
        textBuffer.undo();
        assertEquals(HELLO,textBuffer.toString());
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
    void limitedBufferSizeTest(){
        textBuffer.append(" ");
        textBuffer.append("everyone");
        textBuffer.append(" on this planet");
        textBuffer.replace(HELLO, "Bye");
        textBuffer.append(".");
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        textBuffer.undo();
        assertEquals("hello ",textBuffer.toString());
    }

    @Test
    void redoTest(){
        textBuffer.append(WORLD);
        textBuffer.undo();
        textBuffer.redo();
        assertEquals("helloworld",textBuffer.toString());
    }

    @Test
    void loadFileTest(){
        textBuffer.append(". ");
        textBuffer.loadFile("src/test/testfile.txt");
        assertEquals("hello. Hi, this is a test file.",textBuffer.toString());
    }

    @Test
    void saveFileTest(){
        textBuffer.append(". This is a test to save a file");
        textBuffer.saveFile("src/test/savefile.txt");
        File file = new File("src/test/savefile.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String string;
            while ((string = br.readLine()) != null) {
                builder.append(string);
            }
            assertEquals(builder.toString(),textBuffer.toString());
        } catch (IOException e) {
            fail();
        }
    }

}
