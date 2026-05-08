package parsing;

public class DisplayStatement extends Statement {
    String a;
    public DisplayStatement(String a) {
        this.a = a;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.DisplayStatement.getCode(), helper.getOptimizedHandle(this.a)};
    }
}