package buffermanager;

import java.util.ArrayList;
import java.util.List;

public class TextBuffer implements TextBufferInterface {

    private List<StringBuilder> states;
    private int counter;

    public TextBuffer(String text) {
        this.states = new ArrayList<>();
        this.states.add(new StringBuilder(text));
        this.counter = 0;
    }

    @Override
    public void insert(int i, String str) {
        this.deleteStates();
        StringBuilder newState = new StringBuilder(this.states.get(counter).toString());
        this.states.add(newState.insert(i, str));
        this.counter++;
    }

    @Override
    public void append(String string) {
        this.deleteStates();
        StringBuilder newState = new StringBuilder(this.states.get(counter).toString());
        this.states.add(newState.append(string));
        this.counter++;
    }

    @Override
    public void erase(int n, int i) {
        this.deleteStates();
        StringBuilder newState = new StringBuilder(this.states.get(counter).toString());

        this.states.add(newState.delete(i, i + n));
        this.counter++;

    }

    @Override
    public void eraseTrailing(int n) {
        this.deleteStates();
        int l = this.states.get(counter).length();
        StringBuilder newState = new StringBuilder(this.states.get(counter).substring(0, l - n));
        this.states.add(newState);
        this.counter++;
    }

    @Override
    public void replace(String oldString, String newString) {
        this.deleteStates();
        String newState = this.states.get(counter).toString().replaceAll(oldString, newString);
        this.states.add(new StringBuilder(newState));
        this.counter++;
    }

    @Override
    public void redo() {
        if (this.counter < states.size()) {
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
        // implement later
    }

    @Override
    public void saveFile(String path) {
        // implement later
    }

    private void deleteStates() {

        if (this.states.size() > this.counter + 1) {
            this.states.subList(this.counter + 1, this.states.size()).clear();
        }
    }

    @Override
    public String toString() {
        return this.states.get(this.counter).toString();
    }
}
