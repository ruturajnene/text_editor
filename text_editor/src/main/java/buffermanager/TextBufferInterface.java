package buffermanager;

public interface TextBufferInterface {

    void insert(int i ,String str);

    void append(String string);

    void erase(int n, int i);

    void eraseTrailing(int n);

    void replace(String oldString, String newString);

    void redo();

    void undo();

    void loadFile(String path);

    void saveFile(String path);
}
