package parsing;

public class GetMemorySizeStatement extends ValueStatement {
    public GetMemorySizeStatement() {
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.GetMemorySizeStatement.getCode(), helper.getOptimizedHandle(this.handle) };
    }
}