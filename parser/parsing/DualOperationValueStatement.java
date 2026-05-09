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

    public static DualOperationValueStatement loadFromInstruction(Object[] instruction) {
        String handle = String.valueOf(instruction[1]);
        String a = String.valueOf(instruction[2]);
        Operation op = Operation.values()[Integer.parseInt(instruction[3].toString())];
        String b = String.valueOf(instruction[4]);
        DualOperationValueStatement stmt = new DualOperationValueStatement(a, b, op);
        stmt.generateHandle(handle);
        return stmt;
    }
    public void execute(RunningContext context){
        Object valueA = context.variables.get(this.a);
        Object valueB = context.variables.get(this.b);
        Object result = null;
        switch (this.op) {
            case PLUS:
                if (valueA == null || valueB == null) {
                    result = toStr(valueA) + toStr(valueB);
                } else if (valueA instanceof String || valueB instanceof String) {
                    result = toStr(valueA) + toStr(valueB);
                } else {
                    result = toNumber(valueA) + toNumber(valueB);
                }
                break;
            case MINUS:
                if (valueA instanceof String || valueB instanceof String) {
                    throw new Error("Cannot subtract strings");
                }
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot subtract null values");
                }
                result = toNumber(valueA) - toNumber(valueB);
                break;
            case MUL:
                if (valueA instanceof String || valueB instanceof String) {
                    throw new Error("Cannot multiply strings");
                }
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot multiply null values");
                }
                result = toNumber(valueA) * toNumber(valueB);
                break;
            case DIV:
                if (valueA instanceof String || valueB instanceof String) {
                    throw new Error("Cannot divide strings");
                }
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot divide null values");
                }
                if (toNumber(valueB) == 0.0f) {
                    throw new Error("Division by zero");
                }
                result = toNumber(valueA) / toNumber(valueB);
                break;
            case EQUALITY:
                if (valueA == null && valueB == null) {
                    result = true;
                } else if (valueA == null || valueB == null) {
                    result = false;
                } else {
                    result = valueA.equals(valueB);
                }
                break;
            case INEQUALITY:
                if (valueA == null && valueB == null) {
                    result = false;
                } else if (valueA == null || valueB == null) {
                    result = true;
                } else {
                    result = !valueA.equals(valueB);
                }
                break;
            case LESS:
                if (valueA instanceof String || valueB instanceof String) {
                    throw new Error("Cannot compare strings with <");
                }
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot compare null values with <");
                }
                result = toNumber(valueA) < toNumber(valueB);
                break;
            case LESS_EQUAL:
                if (valueA instanceof String || valueB instanceof String) {
                    throw new Error("Cannot compare strings with <=");
                }
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot compare null values with <=");
                }
                result = toNumber(valueA) <= toNumber(valueB);
                break;
            case GREATER:
                if (valueA instanceof String || valueB instanceof String) {
                    throw new Error("Cannot compare strings with >");
                }
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot compare null values with >");
                }
                result = toNumber(valueA) > toNumber(valueB);
                break;
            case GREATER_EQUAL:
                if (valueA instanceof String || valueB instanceof String) {
                    throw new Error("Cannot compare strings with >=");
                }
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot compare null values with >=");
                }
                result = toNumber(valueA) >= toNumber(valueB);
                break;
            case AND:
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot apply 'and' to null values");
                }
                if (!(valueA instanceof Boolean) || !(valueB instanceof Boolean)) {
                    throw new Error("Cannot apply 'and' to non-boolean values");
                }
                result = (Boolean) valueA && (Boolean) valueB;
                break;
            case OR:
                if (valueA == null || valueB == null) {
                    throw new Error("Cannot apply 'or' to null values");
                }
                if (!(valueA instanceof Boolean) || !(valueB instanceof Boolean)) {
                    throw new Error("Cannot apply 'or' to non-boolean values");
                }
                result = (Boolean) valueA || (Boolean) valueB;
                break;
        }
        context.variables.put(this.handle, result);
    }

    public static Float toNumber(Object value) {
        if (value instanceof Float) return (Float) value;
        if (value instanceof Double) return ((Double) value).floatValue();
        if (value instanceof Integer) return ((Integer) value).floatValue();
        if (value instanceof String) return Float.parseFloat((String) value);
        return null;
    }

    public static String toStr(Object value) {
        return value == null ? "null" : value.toString();
    }
}