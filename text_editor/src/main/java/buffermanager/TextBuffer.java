package buffermanager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The type Text buffer.
 */
public class TextBuffer implements TextBufferInterface {

    /**
     * The size of the Text Buffer
     */
    private static final int BUFFER_SIZE = 5;
    /**
     * Logger to log any error, info
     */
    private Logger logger = Logger.getLogger(this.getClass().getName());
    /**
     * The list of the states of the text buffer
     */
    private List<StringBuilder> states;
    /**
     * Counter to keep track of undo/redo
     */
    private int counter;

    private static final String ERROR_MESSAGE="Index out of bounds, please enter a valid index";

    /**
     * Instantiates a new Text buffer.
     *
     * @param text the text
     */
    public TextBuffer(String text) {
        this.states = new ArrayList<>();
        this.states.add(new StringBuilder(text));
        this.counter = 0;
    }

    @Override
    public void insert(int i, String str) {
        this.deleteStates();
        this.updateStateBuffer();
        try {
            StringBuilder newState = new StringBuilder(this.states.get(counter).toString());
            this.states.add(newState.insert(i, str));
            this.counter++;
        }catch (StringIndexOutOfBoundsException e){
            logger.info(ERROR_MESSAGE);
        }

    }

    @Override
    public void append(String string) {
        this.deleteStates();
        this.updateStateBuffer();
        StringBuilder newState = new StringBuilder(this.states.get(counter).toString());
        this.states.add(newState.append(string));
        this.counter++;
    }

    @Override
    public void erase(int n, int i) {
        this.deleteStates();
        this.updateStateBuffer();
        StringBuilder newState = new StringBuilder(this.states.get(counter).toString());

        try {
            this.states.add(newState.delete(i, i + n));
            this.counter++;
        }catch (StringIndexOutOfBoundsException e){
            logger.info(ERROR_MESSAGE);
        }

    }

    @Override
    public void eraseTrailing(int n) {
        this.deleteStates();
        this.updateStateBuffer();
        int l = this.states.get(counter).length();
        try {
            StringBuilder newState = new StringBuilder(this.states.get(counter).substring(0, l - n));
            this.states.add(newState);
            this.counter++;
        }
        catch (Exception e){
            logger.info("Please enter value less than the number of characters present");
        }
    }

    @Override
    public void replace(String oldString, String newString) {
        this.deleteStates();
        this.updateStateBuffer();
        String newState = this.states.get(counter).toString().replaceAll(oldString, newString);
        this.states.add(new StringBuilder(newState));
        this.counter++;
    }

    @Override
    public void redo() {
        if (this.counter < states.size()-1) {
            this.counter++;
        }
    }

    @Override
    public void undo() {
        if (this.counter > 0) {
            this.counter--;
        }
    }

    @Override
    public void loadFile(String path) {
        this.deleteStates();
        this.updateStateBuffer();
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder newState = new StringBuilder(this.states.get(this.counter));
            String string;
            while ((string = br.readLine()) != null) {
                newState.append(string);
            }
            this.states.add(newState);
            this.counter++;
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void saveFile(String path) {
        String content = this.states.get(this.counter).toString();
        try {
            Files.write(Paths.get(path), content.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    /**
     * Helper method to invalidate old operations
     * when an operation is performed after an undo
     */
    private void deleteStates() {

        if (this.states.size() > this.counter + 1) {
            this.states.subList(this.counter + 1, this.states.size()).clear();
        }
    }

    /**
     * Helper method to update the buffer when the max size was reached.
     */
    private void updateStateBuffer() {
        if (this.states.size() == BUFFER_SIZE) {
            this.counter--;
            this.states.remove(0);
        }
    }

    @Override
    public String toString() {
        return this.states.get(this.counter).toString();
    }
}
