import java.io.*;
import java.util.ArrayList;

import parsing.RunningContext;
import parsing.Statements;
   
public class Runner {

    public static native void setRunningContext(RunningContext runningContext);

    static public void main(String argv[]) {    
        try {
            String encoded = argv[0];
            ArrayList<Object[]> decoded = InstructionsCodec.decode(encoded);
            Statements statements = Statements.loadFromInstructions(decoded);
            RunningContext context = new RunningContext(statements);
            new Thread(() -> {
                setRunningContext(context);
            }).start();
            context.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


