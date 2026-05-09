package parsing;

public class AddToMemoryStatement extends Statement {
    String a;
    public AddToMemoryStatement(String a) {
        this.a = a;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.AddToMemoryStatement.getCode(), helper.getOptimizedHandle(this.a)};
    }

    public static AddToMemoryStatement loadFromInstruction(Object[] instruction) {
        String a = String.valueOf(instruction[1]);
        return new AddToMemoryStatement(a);
    }
    public void execute(RunningContext context){
        Object value = context.variables.get(this.a);
        RunningContext.addToMemory(value);
    }
}