package parsing;

public class ButtonStatement extends Statement {
    String a;
    String blockName;
    public ButtonStatement(String a, String blockName) {
        this.a = a;
        this.blockName = blockName;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.ButtonStatement.getCode(), helper.getOptimizedHandle(this.a)};
    }
}