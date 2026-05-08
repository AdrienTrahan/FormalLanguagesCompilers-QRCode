package parsing;

public class ClearScreenStatement extends Statement {
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.ClearScreenStatement.getCode() };
    }
}