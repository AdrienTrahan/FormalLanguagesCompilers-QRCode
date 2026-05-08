package parsing;

public class ConditionalGotoStatement extends Statement {
    String handle;
    String blockNameA;
    String blockNameB;

    public ConditionalGotoStatement(String handle, String blockNameA) {
        this(handle, blockNameA, null);
    }

    public ConditionalGotoStatement(String handle, String blockNameA, String blockNameB) {
        this.handle = handle;
        this.blockNameA = blockNameA;
        this.blockNameB = blockNameB;
    }
    public Object[] toList(ParserHelper helper) {
        if (this.blockNameB == null) return new Object[]{ Statement.StatementType.ConditionalGotoStatement.getCode(), helper.getOptimizedHandle(this.handle), helper.getOptimizedFunctionName(this.blockNameA) };
        return new Object[]{ Statement.StatementType.ConditionalGotoStatement.getCode(), helper.getOptimizedHandle(this.handle), helper.getOptimizedFunctionName(this.blockNameA), helper.getOptimizedFunctionName(this.blockNameB) };
    }
}