package parsing;

public class GetMemoryAtStatement extends ValueStatement {
    String a;
    public GetMemoryAtStatement(String a) {
        this.a = a;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.GetMemoryAtStatement.getCode(), helper.getOptimizedHandle(this.handle), helper.getOptimizedHandle(this.a)};
    }

    public static GetMemoryAtStatement loadFromInstruction(Object[] instruction) {
        String handle = String.valueOf(instruction[1]);
        String a = String.valueOf(instruction[2]);
        GetMemoryAtStatement stmt = new GetMemoryAtStatement(a);
        stmt.generateHandle(handle);
        return stmt;
    }
    
public void execute(RunningContext context){
        Object value = context.variables.get(this.a);
        int index = -1;
        if (value instanceof Boolean) {
            index = (Boolean) value ? 1 : 0;
        } else if (value instanceof Float) {
            float f = (Float) value;
            if (f != Math.floor(f)) {
                throw new Error("Memory index must be an integer");
            }
            index = (int) f;
        } else {
            throw new Error("Memory index must be a number or boolean");
        }
        Object result = RunningContext.getMemoryAt(index);
        context.variables.put(this.handle, result);
    }
}