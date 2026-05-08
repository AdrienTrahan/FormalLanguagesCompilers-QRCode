package parsing;

public class AddToMemoryStatement extends Statement {
    String a;
    public AddToMemoryStatement(String a) {
        this.a = a;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.AddToMemoryStatement.getCode(), helper.getOptimizedHandle(this.a)};
    }
}