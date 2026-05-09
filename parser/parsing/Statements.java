package parsing;

import java.util.ArrayList;
import java.util.List;

public class Statements {
    public List<Statement> statements = new ArrayList<>();
    public Statements() {
    }
    public void appendStatements(Statements b){
        this.statements.addAll(b.statements);
    }
    public String getHandle(){
        if (statements.isEmpty()) return null;
        Statement lastStatement = statements.get(statements.size() - 1);
        if (lastStatement instanceof ValueStatement) return ((ValueStatement) lastStatement).handle;
        return null;
    }
    public static Statements loadFromInstructions(ArrayList<Object[]> instructions){
        Statements statements = new Statements();
        for (Object[] instruction : instructions) {
            Statement statement = Statement.loadFromInstruction(instruction);
            statements.statements.add(statement);
        }
        return statements;

    }
}