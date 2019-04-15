package buffermanager;

/**
 * The interface Operation.
 */
public interface Operation {

    /**
     * Method to execute the operation on the buffer.
     *
     * @param buffer the buffer
     */
    void execute(StringBuilder buffer);
}