package parsing;

public class GotoStatement extends Statement {
    String blockName;
    public GotoStatement(String blockName){
        this.blockName = blockName;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.GotoStatement.getCode(), helper.getOptimizedFunctionName(this.blockName) };
    }
}