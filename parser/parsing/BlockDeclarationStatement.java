package parsing;

public class BlockDeclarationStatement extends Statement {
    public String blockName;
    public BlockDeclarationStatement(String blockName){
        this.blockName = blockName;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.BlockStart.getCode(), helper.getOptimizedFunctionName(this.blockName) };
    }
    
    public static BlockDeclarationStatement loadFromInstruction(Object[] instruction) {
        return new BlockDeclarationStatement(String.valueOf(instruction[1]));
    }
    public void execute(RunningContext context){
    }
}