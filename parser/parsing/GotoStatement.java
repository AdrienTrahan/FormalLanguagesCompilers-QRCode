package parsing;

public class GotoStatement extends Statement {
    String blockName;
    public GotoStatement(String blockName){
        this.blockName = blockName;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.GotoStatement.getCode(), helper.getOptimizedFunctionName(this.blockName) };
    }

    public static GotoStatement loadFromInstruction(Object[] instruction) {
        return new GotoStatement(String.valueOf(instruction[1]));
    }

    public void execute(RunningContext context){
        Statements statements = context.blocksTable.get(this.blockName);
        if (statements == null) throw new Error("Block does not exist");
        context.topLevelStatements.statements.addAll(0, statements.statements);
    }
}