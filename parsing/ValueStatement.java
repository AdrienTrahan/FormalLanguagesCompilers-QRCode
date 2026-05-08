package parsing;

public abstract class ValueStatement extends Statement {
    private static int counter = 0;
    public String handle;
    public ValueStatement(){
        this.handle = String.valueOf(counter++);
    }
    public ValueStatement(String handle) {
        this.handle = handle;
    }
    public void generateHandle(String handle) {
        this.handle = handle;
    }
}