package buffermanager;

/**
 * The type Replace operation.
 */
public class ReplaceOperation implements Operation {


    /**
     * The string to be replaced
     */
    private String old;
    /**
     * The text to replace the given string
     */
    private String text;

    /**
     * Instantiates a new Replace operation.
     *
     * @param old  the string to be replaced
     * @param text the text to replace all occurrences of the given string
     */
    ReplaceOperation(String old,String text){
        this.old=old;
        this.text=text;
    }
    @Override
    public StringBuilder execute(StringBuilder buffer) {
        int i =buffer.indexOf(old);
        int len=old.length();
        while(i!=-1){
            buffer.replace(i,i+len,text);
            i=buffer.indexOf(old);
        }
        return buffer;
    }
}
