package parsing;

public abstract class Statement {
    public enum StatementType {
        BlockStart(0),
        GotoStatement(1),
        ConditionalGotoStatement(2),
        ConstantValueStatement(3),
        VariableValueStatement(4),
        DualOperationValueStatement(5),
        NotOperationValueStatement(6),
        AssignmentStatement(7),
        ClearMemoryStatement(8),
        ClearScreenStatement(9),
        DisplayStatement(10),
        ButtonStatement(11),
        AddToMemoryStatement(12),
        GetMemoryAtStatement(13),
        GetMemorySizeStatement(14);

        private final int code;
        StatementType(int code) {
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
    public abstract Object[] toList(ParserHelper helper);
}