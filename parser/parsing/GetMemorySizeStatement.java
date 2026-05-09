package parsing;

public class GetMemorySizeStatement extends ValueStatement {
    public GetMemorySizeStatement() {
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.GetMemorySizeStatement.getCode(), helper.getOptimizedHandle(this.handle) };
    }

    public static GetMemorySizeStatement loadFromInstruction(Object[] instruction) {
        String handle = String.valueOf(instruction[1]);
        GetMemorySizeStatement stmt = new GetMemorySizeStatement();
        stmt.generateHandle(handle);
        return stmt;
    }
    public void execute(RunningContext context){
        Object result = RunningContext.getMemorySize();
        context.variables.put(this.handle, result);
    }
}