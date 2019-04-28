package buffermanager;

/**
 * The interface Operation.
 */
public interface Operation {

    /**
     * Method to execute the operation on the buffer.
     *
     * @param buffer the buffer
     *
     * @return the updated buffer
     */
    StringBuilder execute(StringBuilder buffer);

    /**
     * Method to undo the current operation on the buffer
     * @param buffer the buffer
     * @return the updated buffer
     */
    StringBuilder undo(StringBuilder buffer);
}
