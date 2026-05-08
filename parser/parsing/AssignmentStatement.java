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
}