package parsing;

public class ButtonStatement extends Statement {
    String a;
    String blockName;
    public ButtonStatement(String a, String blockName) {
        this.a = a;
        this.blockName = blockName;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.ButtonStatement.getCode(), helper.getOptimizedHandle(this.a), helper.getOptimizedFunctionName(this.blockName) };
    }

    public static ButtonStatement loadFromInstruction(Object[] instruction) {
        String a = String.valueOf(instruction[1]);
        String blockName = String.valueOf(instruction[2]);
        return new ButtonStatement(a, blockName);
    }
    public void execute(RunningContext context){
        Object value = context.variables.get(this.a);
        if (value == null) RunningContext.button("", this.blockName);
        else RunningContext.button(value.toString(), this.blockName);
    }
}