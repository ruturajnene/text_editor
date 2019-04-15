package buffermanager;

public enum OperationType {

    INSERT("insert"),
    ERASE("erase"),
    REPLACE("replace");

    private String operation;

    OperationType(String operation) {
        this.operation = operation;
    }


    @Override
    public String toString() {
        return operation;
    }
}
