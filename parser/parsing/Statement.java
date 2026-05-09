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
    static Statement loadFromInstruction(Object[] instruction) {
        if (instruction[0] == null) return null;
        StatementType statementType = StatementType.values()[Integer.parseInt(instruction[0].toString())];
        switch (statementType) {
            case BlockStart:
                return BlockDeclarationStatement.loadFromInstruction(instruction);
            case GotoStatement:
                return GotoStatement.loadFromInstruction(instruction);
            case ConditionalGotoStatement:
                return ConditionalGotoStatement.loadFromInstruction(instruction);
            case ConstantValueStatement:
                return ConstantValueStatement.loadFromInstruction(instruction);
            case VariableValueStatement:
                return VariableValueStatement.loadFromInstruction(instruction);
            case DualOperationValueStatement:
                return DualOperationValueStatement.loadFromInstruction(instruction);
            case NotOperationValueStatement:
                return NotOperationValueStatement.loadFromInstruction(instruction);
            case AssignmentStatement:
                return AssignmentStatement.loadFromInstruction(instruction);
            case ClearMemoryStatement:
                return ClearMemoryStatement.loadFromInstruction(instruction);
            case ClearScreenStatement:
                return ClearScreenStatement.loadFromInstruction(instruction);
            case DisplayStatement:
                return DisplayStatement.loadFromInstruction(instruction);
            case ButtonStatement:
                return ButtonStatement.loadFromInstruction(instruction);
            case AddToMemoryStatement:
                return AddToMemoryStatement.loadFromInstruction(instruction);
            case GetMemoryAtStatement:
                return GetMemoryAtStatement.loadFromInstruction(instruction);
            case GetMemorySizeStatement:
                return GetMemorySizeStatement.loadFromInstruction(instruction);
        }
        return null;
        
    }
}