package parsing;

public class ClearMemoryStatement extends Statement {
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.ClearMemoryStatement.getCode() };
    }

    public static ClearMemoryStatement loadFromInstruction(Object[] instruction) {
        return new ClearMemoryStatement();
    }
    public void execute(RunningContext context){
        RunningContext.clearMemory();
    }
}