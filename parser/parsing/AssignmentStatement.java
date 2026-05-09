package parsing;

public class AssignmentStatement extends Statement {
    String a;
    String b;
    public AssignmentStatement(String a, String b){
        this.a = a;
        this.b = b;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.AssignmentStatement.getCode(), helper.getOptimizedHandle(this.a), helper.getOptimizedHandle(this.b) };
    }

    public static AssignmentStatement loadFromInstruction(Object[] instruction) {
        String a = String.valueOf(instruction[1]);
        String b = String.valueOf(instruction[2]);
        return new AssignmentStatement(a, b);
    }
    public void execute(RunningContext context){
        Object value = context.variables.get(this.b);
        context.variables.put(this.a, value);
    }
}