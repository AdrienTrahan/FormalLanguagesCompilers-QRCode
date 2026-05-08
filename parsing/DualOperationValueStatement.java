package parsing;

public class DualOperationValueStatement extends ValueStatement {
    String a;
    String b;
    Operation op;
    public enum Operation {
        PLUS(0),
        MINUS(1),
        MUL(2),
        DIV(3),
        EQUALITY(4),
        INEQUALITY(5),
        LESS(6),
        LESS_EQUAL(7),
        GREATER(8),
        GREATER_EQUAL(9),
        AND(10),
        OR(11);
        private final int code;
        Operation(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
        @Override
        public String toString() {
            return String.valueOf(code);
        }
    }
    public DualOperationValueStatement(String a, String b, Operation op){
        super();
        this.a = a;
        this.b = b;
        this.op = op;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.DualOperationValueStatement.getCode(), helper.getOptimizedHandle(this.handle), helper.getOptimizedHandle(this.a), this.op.getCode(), helper.getOptimizedHandle(this.b) };
    }
}