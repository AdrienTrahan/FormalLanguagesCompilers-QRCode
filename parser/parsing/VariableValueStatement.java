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
}