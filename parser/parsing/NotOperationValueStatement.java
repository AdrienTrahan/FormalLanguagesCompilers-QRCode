package parsing;

public class NotOperationValueStatement extends ValueStatement {
    String a;
    public NotOperationValueStatement(String a){
        super();
        this.a = a;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.NotOperationValueStatement.getCode(), helper.getOptimizedHandle(this.handle), helper.getOptimizedHandle(this.a) };
    }

    public static NotOperationValueStatement loadFromInstruction(Object[] instruction) {
        String handle = String.valueOf(instruction[1]);
        String a = String.valueOf(instruction[2]);
        NotOperationValueStatement stmt = new NotOperationValueStatement(a);
        stmt.generateHandle(handle);
        return stmt;
    }

    public void execute(RunningContext context){
        Object value = context.variables.get(this.a);
        if (!(value instanceof Boolean)) throw new Error("Cannot apply 'not' to a non-boolean value");
        Object result = !((Boolean) value);
        context.variables.put(this.handle, result);
    }
}