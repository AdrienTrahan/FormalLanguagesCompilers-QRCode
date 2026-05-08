package parsing;

public class GetMemoryAtStatement extends ValueStatement {
    String a;
    public GetMemoryAtStatement(String a) {
        this.a = a;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.GetMemoryAtStatement.getCode(), helper.getOptimizedHandle(this.handle), helper.getOptimizedHandle(this.a)};
    }
}