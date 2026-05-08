package parsing;

public class ClearMemoryStatement extends Statement {
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.ClearMemoryStatement.getCode() };
    }
}