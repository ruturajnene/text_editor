package buffermanager;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
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
    private Deque<Operation> states;
    /**
     * Stack to keep track of undo/redo operations
     */

    private Deque<Operation> operationStack;


    /**
     * The current text state of the buffer
     */
    private StringBuilder currentText;


    /**
     * Instantiates a new Text buffer.
     *
     * @param text the text
     */
    TextBuffer(String text) {
        this.states = new LinkedList<>();
        this.operationStack = new LinkedList<>();
        this.currentText = new StringBuilder(text);
    }

    @Override
    public void insert(int i, String str) {
        this.deleteStates();
        this.updateStateBuffer();
        Operation op = new InsertOperation(i, str);
        this.currentText = op.execute(this.currentText);
        this.states.add(op);
    }

    @Override
    public void append(String string) {
        this.insert(-1, string);
    }

    @Override
    public void erase(int i, int n) {
        this.deleteStates();
        this.updateStateBuffer();
        Operation op = new EraseOperation(i, n);
        this.currentText = op.execute(this.currentText);
        this.states.add(op);
    }

    @Override
    public void eraseTrailing(int n) {
        this.erase(-1, n);
    }

    @Override
    public void replace(String oldString, String newString) {
        this.deleteStates();
        this.updateStateBuffer();
        Operation op = new ReplaceOperation(oldString, newString);
        this.currentText = op.execute(this.currentText);
        this.states.add(op);
    }

    @Override
    public void redo() {
        if (!this.operationStack.isEmpty()) {
            Operation operation = this.operationStack.pollLast();
            this.currentText = operation.execute(this.currentText);
            this.operationStack.add(operation);
        }
    }

    @Override
    public void undo() {
        if (!this.states.isEmpty()) {
            Operation operation = this.states.pollLast();
            this.currentText = operation.undo(this.currentText);
            this.operationStack.add(operation);
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
            this.currentText = newState;
            this.states = new LinkedList<>();
            this.operationStack = new LinkedList<>();
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
        this.operationStack.clear();
    }

    /**
     * Helper method to update the buffer when the max size was reached.
     */
    private void updateStateBuffer() {
        if (this.states.size() == BUFFER_SIZE) {
            this.states.pop();
        }
    }

    @Override
    public String toString() {
        return this.currentText.toString();
    }
}
