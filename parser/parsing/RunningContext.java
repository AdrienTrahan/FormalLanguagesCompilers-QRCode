package parsing;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class RunningContext {

    public static native void display(String string);
    public static native void clearMemory();
    public static native void clearScreen();
    public static native void button(String string, String blockName);
    public static native Object getMemoryAt(Integer index);
    public static native Float getMemorySize();
    public static native void addToMemory(Object value);

    public Statements topLevelStatements = new Statements();
    public HashMap <String, Statements> blocksTable = new HashMap<String, Statements>();
    public HashMap <String, Object> variables = new HashMap<String, Object>();
    public ArrayList<String> queuedBlockNames = new ArrayList<String>();

    public boolean isRunning = false;

    public RunningContext(Statements statements) {
        String currentBlockName = null;
        for (Statement statement : statements.statements) {
            if (statement instanceof BlockDeclarationStatement blockDeclarationStatement){
                currentBlockName = blockDeclarationStatement.blockName;
                blocksTable.put(currentBlockName, new Statements());
                continue;
            }
            if (currentBlockName == null){
                topLevelStatements.statements.add(statement);
                continue;
            }
            blocksTable.get(currentBlockName).statements.add(statement);
        }
    }

    public void execute() {
        isRunning = true;
        while (this.topLevelStatements.statements.size() > 0) {
            this.queuedBlockNames.clear();
            Statement statement = this.topLevelStatements.statements.get(0);
            this.topLevelStatements.statements.remove(0);
            statement.execute(this);
        }
        isRunning = false;
    }

    public void queueBlockName(String blockName){
        this.topLevelStatements.statements.addAll(this.blocksTable.get(blockName).statements);
        if (!isRunning) execute();
    }
}
