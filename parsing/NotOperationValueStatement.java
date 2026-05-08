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
}