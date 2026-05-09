package parsing;

public class DisplayStatement extends Statement {
    String a;
    public DisplayStatement(String a) {
        this.a = a;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.DisplayStatement.getCode(), helper.getOptimizedHandle(this.a)};
    }

    public static DisplayStatement loadFromInstruction(Object[] instruction) {
        String a = String.valueOf(instruction[1]);
        return new DisplayStatement(a);
    }
    public void execute(RunningContext context){
        Object value = context.variables.get(this.a);
        if (value == null) RunningContext.display("");
        else RunningContext.display(value.toString());
    }
}