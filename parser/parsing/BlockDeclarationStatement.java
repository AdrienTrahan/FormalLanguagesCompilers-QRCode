package parsing;

public class BlockDeclarationStatement extends Statement {
    String blockName;
    public BlockDeclarationStatement(String blockName){
        this.blockName = blockName;
    }
    public Object[] toList(ParserHelper helper) {
        return new Object[]{ Statement.StatementType.BlockStart.getCode(), helper.getOptimizedFunctionName(this.blockName) };
    }
    
    public static BlockDeclarationStatement loadFromInstruction(Object[] instruction) {
        return new BlockDeclarationStatement((String) instruction[1]);
    }
}