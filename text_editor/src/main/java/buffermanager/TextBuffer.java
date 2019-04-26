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
    private List<Operation> states;
    /**
     * Counter to keep track of undo/redo
     */
    private int counter;

    /**
     * The initial text state of the buffer
     */
    private String text;

    /**
     * Instantiates a new Text buffer.
     *
     * @param text the text
     */
    public TextBuffer(String text) {
        this.states = new ArrayList<>();
        this.counter = -1;
        this.text=text;
    }

    @Override
    public void insert(int i, String str) {
        this.deleteStates();
        this.updateStateBuffer();
        this.states.add(new InsertOperation(i,str));
        this.counter++;
    }

    @Override
    public void append(String string) {
        this.insert(-1,string);
    }

    @Override
    public void erase(int i, int n) {
        this.deleteStates();
        this.updateStateBuffer();
        this.states.add(new EraseOperation(i,n));
        this.counter++;
    }

    @Override
    public void eraseTrailing(int n) {
        this.erase(-1,n);
    }

    @Override
    public void replace(String oldString, String newString) {
        this.deleteStates();
        this.updateStateBuffer();
        this.states.add(new ReplaceOperation(oldString,newString));
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
        if (this.counter > -1) {
            this.counter--;
        }
    }

    @Override
    public void loadFile(String path) {
        File file = new File(path);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            StringBuilder newState = new StringBuilder();
            String string;
            while ((string = br.readLine()) != null) {
                newState.append(string);
            }
            this.text=newState.toString();
            this.states= new ArrayList<>();
            this.counter=-1;
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
    }

    @Override
    public void saveFile(String path) {
        String content = this.toString();
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
            StringBuilder initialState= new StringBuilder(text);
            this.states.get(0).execute(initialState);
            this.text=initialState.toString();
            this.states.remove(0);
        }
    }

    @Override
    public String toString() {
        if(this.counter!=-1){
            StringBuilder state= new StringBuilder(this.text);
            for (int i=0; i<=counter; i++) {
                this.states.get(i).execute(state);
            }
            return state.toString();
        }
        else
            return this.text;
    }
}
