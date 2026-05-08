import java.io.*;
import java.util.ArrayList;
   
public class Encode {
    static public void main(String argv[]) {    
        try {
            Yylex l = new Yylex(new FileReader(argv[0]));
            parser p = new parser(l);
            Object result = p.parse();
            ArrayList<Object[]> instructions = p.getGeneratedByteCode();

            String encoded = InstructionsCodec.encode(instructions);

            System.out.println(encoded);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


