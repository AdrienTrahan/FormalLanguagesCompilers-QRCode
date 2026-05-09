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

    public static ConditionalGotoStatement loadFromInstruction(Object[] instruction) {
        String handle = String.valueOf(instruction[1]);
        String blockNameA = String.valueOf(instruction[2]);
        if (instruction.length > 3 && instruction[3] != null) {
            String blockNameB = String.valueOf(instruction[3]);
            return new ConditionalGotoStatement(handle, blockNameA, blockNameB);
        }
        return new ConditionalGotoStatement(handle, blockNameA);
    }
    public void execute(RunningContext context){
        if (context.variables.get(this.handle) instanceof Boolean condition && condition != null) {
            if (condition) {
                Statements statements = context.blocksTable.get(this.blockNameA);
                if (statements == null) throw new Error("Block does not exist");
                context.topLevelStatements.statements.addAll(0, statements.statements);
            }else if (this.blockNameB != null) {
                Statements statements = context.blocksTable.get(this.blockNameB);
                if (statements == null) throw new Error("Block does not exist");
                context.topLevelStatements.statements.addAll(0, statements.statements);
            }
        }else throw new Error("Expression does not produce a boolean value");
    }
}