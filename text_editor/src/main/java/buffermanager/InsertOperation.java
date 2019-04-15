package buffermanager;

import java.util.logging.Logger;

/**
 * The type Insert operation.
 */
public class InsertOperation implements Operation{

    /**
     * The position to be inserted at
     */
    private int position;
    /**
     * the text to be inserted at
     */
    private String text;

    /**
     * Logger to log any error, info
     */
    private Logger logger = Logger.getLogger(this.getClass().getName());

    /**
     * Instantiates a new Insert operation.
     *
     * @param position the position in the sequence
     * @param text     the text to be inserted at the given position
     */
    InsertOperation(int position,String text){
        this.position=position;
        this.text=text;
    }

    @Override
    public void execute(StringBuilder buffer) {
        if(position!=-1) {
            try {
                buffer.insert(position, text);
            }catch (StringIndexOutOfBoundsException e){
            logger.info("Index out of bounds, please enter a valid index");
        }
        }
        else{
            buffer.append(text);
        }
    }
}
