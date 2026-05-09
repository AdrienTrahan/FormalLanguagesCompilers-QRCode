import java.io.*;
import java.util.ArrayList;

public class Compiler {
    public static native void sendOutput(String s);
    
    public static void main(String[] args) {
        try {
            Yylex l = new Yylex(new StringReader(args[0]));
            parser p = new parser(l);
            p.parse();
            ArrayList<Object[]> instructions = p.getGeneratedByteCode();
            String encoded = InstructionsCodec.encode(instructions);
            sendOutput(encoded);
        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(1);
        }
    }
}