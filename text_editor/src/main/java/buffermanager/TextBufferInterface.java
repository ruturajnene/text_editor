package buffermanager;

/**
 * The interface Text buffer interface.
 */
public interface TextBufferInterface {

    /**
     * Insert the string at the given index.
     *
     * @param i   the
     * @param str the str
     */
    void insert(int i ,String str);

    /**
     * Append the string to the existing text.
     *
     * @param string the string
     */
    void append(String string);

    /**
     * Erase n characters at the given index i.
     *
     * @param n the number of characters
     * @param i the index
     */
    void erase(int n, int i);

    /**
     * Erase n trailing characters.
     *
     * @param n the n characters
     */
    void eraseTrailing(int n);

    /**
     * Replace all occurrences of the existing string by
     * the given string.
     *
     * @param oldString the old string
     * @param newString the new string
     */
    void replace(String oldString, String newString);

    /**
     * Redo the operation.
     */
    void redo();

    /**
     * Undo the operation.
     */
    void undo();

    /**
     * Load a text file from the given path.
     *
     * @param path the path
     */
    void loadFile(String path);

    /**
     * Save the text in the buffer to a text file.
     *
     * @param path the path
     */
    void saveFile(String path);
}
