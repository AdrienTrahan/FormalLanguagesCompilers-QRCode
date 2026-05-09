package parsing;

public class VariableValueStatement extends ValueStatement {
    String name;
    public VariableValueStatement(String name){
        super();
        this.name = name;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.VariableValueStatement.getCode(), helper.getOptimizedHandle(this.handle), helper.getOptimizedHandle(this.name) };
    }

    public static VariableValueStatement loadFromInstruction(Object[] instruction) {
        String handle = String.valueOf(instruction[1]);
        String name = String.valueOf(instruction[2]);
        VariableValueStatement stmt = new VariableValueStatement(name);
        stmt.generateHandle(handle);
        return stmt;
    }

    public void execute(RunningContext context){
        Object value = context.variables.get(this.name);
        context.variables.put(this.handle, value);
    }
}