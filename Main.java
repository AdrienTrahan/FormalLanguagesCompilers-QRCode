import java.io.*;
import java.util.ArrayList;
   
public class Main {
    static public void main(String argv[]) {    
        try {
            Yylex l = new Yylex(new FileReader(argv[0]));
            parser p = new parser(l);
            Object result = p.parse();
            ArrayList<Object[]> instructions = p.getGeneratedByteCode();

            for (Object[] statement : instructions) {
                for (Object field : statement) {
                    System.out.print(field + " ");
                }
                System.out.println();
            }
            String encoded = InstructionsCodec.encode(instructions);
            System.out.println(encoded);

            ArrayList<Object[]> decoded = InstructionsCodec.decode(encoded);
            System.out.println("SEPARATOR");

            for (Object[] statement : decoded) {
                for (Object field : statement) {
                    System.out.print(field + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


