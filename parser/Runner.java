import java.io.*;
import java.util.ArrayList;

import parsing.Statements;
   
public class Runner {

    public static native void clearMemory();
    public static native void clearScreen();
    public static native void button(String string);
    public static native void display(String string);
    public static native void getMemoryAt(Integer index);
    public static native void getMemorySize();
    public static native void addToMemory(Object value);

    static public void main(String argv[]) {    
        try {
            String encoded = argv[0];
            ArrayList<Object[]> decoded = InstructionsCodec.decode(encoded);
            Statements statements = Statements.loadFromInstructions(decoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


