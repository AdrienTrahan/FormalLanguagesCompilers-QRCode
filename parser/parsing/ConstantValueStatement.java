package parsing;

public class ConstantValueStatement extends ValueStatement {
    Object value;

    public enum ValueType {
        Scalar(0),
        Bool(1),
        String(2);
        private final int code;
        ValueType(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }

    public ConstantValueStatement(Object value){
        super();
        this.value = value;
    }
    public ValueType getValueType(){
        if (this.value instanceof Float) return ValueType.Scalar;
        if (this.value instanceof Boolean) return ValueType.Bool;
        if (this.value instanceof String) return ValueType.String;
        return ValueType.String;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.ConstantValueStatement.getCode(), helper.getOptimizedHandle(this.handle), this.getValueType().getCode(), this.value.toString()};
    }

    public static ConstantValueStatement loadFromInstruction(Object[] instruction) {
        String handle = String.valueOf(instruction[1]);
        int valueTypeCode = Integer.parseInt(instruction[2].toString());
        String valueStr = String.valueOf(instruction[3]);

        ValueType valueType = ValueType.values()[valueTypeCode];
        Object value;
        switch (valueType) {
            case Scalar:
                value = Float.parseFloat(valueStr);
                break;
            case Bool:
                value = Boolean.parseBoolean(valueStr);
                break;
            default:
                value = valueStr;
        }

        ConstantValueStatement stmt = new ConstantValueStatement(value);
        stmt.generateHandle(handle);
        return stmt;
    }
    public void execute(RunningContext context){
        context.variables.put(this.handle, this.value);
    }
}