import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;

public class InstructionsCodec {

    public static String encode(ArrayList<Object[]> instructions) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(byteStream);

        out.writeInt(instructions.size());

        for (Object[] instr : instructions) {
            out.writeInt(instr.length);

            for (Object obj : instr) {
                if (obj instanceof Integer) {
                    out.writeByte(0);
                    out.writeInt((Integer) obj);
                } else {
                    out.writeByte(1);
                    byte[] strBytes = ((String) obj).getBytes(StandardCharsets.UTF_8);
                    out.writeInt(strBytes.length);
                    out.write(strBytes);
                }
            }
        }

        ByteArrayOutputStream compressed = new ByteArrayOutputStream();
        DeflaterOutputStream deflater = new DeflaterOutputStream(compressed);
        deflater.write(byteStream.toByteArray());
        deflater.close();

        return Base64.getEncoder().encodeToString(compressed.toByteArray());
    }

    public static ArrayList<Object[]> decode(String encoded) throws IOException {
        byte[] compressed = Base64.getDecoder().decode(encoded);

        ByteArrayInputStream byteStream = new ByteArrayInputStream(compressed);
        InflaterInputStream inflater = new InflaterInputStream(byteStream);
        byte[] rawBytes = inflater.readAllBytes();
        inflater.close();

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(rawBytes));

        int numInstructions = in.readInt();
        ArrayList<Object[]> instructions = new ArrayList<>();

        for (int i = 0; i < numInstructions; i++) {
            int len = in.readInt();
            Object[] instr = new Object[len];

            for (int j = 0; j < len; j++) {
                int type = in.readByte();
                if (type == 0) {
                    instr[j] = in.readInt();
                } else {
                    int strLen = in.readInt();
                    byte[] strBytes = new byte[strLen];
                    in.readFully(strBytes);
                    instr[j] = new String(strBytes, StandardCharsets.UTF_8);
                }
            }
            instructions.add(instr);
        }

        return instructions;
    }
}