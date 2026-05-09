package parsing;

public class ClearScreenStatement extends Statement {
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.ClearScreenStatement.getCode() };
    }

    public static ClearScreenStatement loadFromInstruction(Object[] instruction) {
        return new ClearScreenStatement();
    }
    public void execute(RunningContext context){
        RunningContext.clearScreen();
    }
}