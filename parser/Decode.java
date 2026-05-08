import java.io.*;
import java.util.ArrayList;
   
public class Decode {
    static public void main(String argv[]) {    
        try {
            String encoded = argv[0];
            ArrayList<Object[]> decoded = InstructionsCodec.decode(encoded);
            
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


