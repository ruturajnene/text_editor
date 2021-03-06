package buffermanager;

import java.util.logging.Logger;

/**
 * The type Erase operation.
 */
public class EraseOperation implements Operation {

    /**
     * The position in the sequence from where to start erasing characters
     */
    private int position;
    /**
     * The number of characters to be erased
     */
    private int characters;

    /**
     * The characters deleted as a result of the operation
     */
    private String deletedCharacters="";
    /**
     * Logger to log any error, info
     */
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Instantiates a new Erase operation.
     *
     * @param position   the position
     * @param characters the number of characters to be erased
     */
    EraseOperation(int position,int characters){
        this.position=position;
        this.characters=characters;
    }
    @Override
    public StringBuilder execute(StringBuilder buffer) {
        try {
            if (position != -1) {
                deletedCharacters=buffer.substring(position, position + characters);
                buffer.delete(position, position + characters);
            } else {
                int l = buffer.length();
                deletedCharacters=buffer.substring(l - characters, l);
                buffer.delete(l - characters, l);
            }
        }catch (Exception e){
            logger.info("Please enter value less than the number of characters present");
        }
        return buffer;
    }

    @Override
    public StringBuilder undo(StringBuilder buffer) {
        try {
            if (position != -1) {
                buffer.insert(position, deletedCharacters);
            } else {
                buffer.append(deletedCharacters);
            }
        }catch (Exception e){
            logger.info("Please enter value less than the number of characters present");
        }

        return buffer;
    }
}
