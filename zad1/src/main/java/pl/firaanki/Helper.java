package pl.firaanki;

import java.util.BitSet;
import java.util.Random;

public class Helper {

    static Random r = new Random();

    static int[][] doubleErrorMatrix =
            {
                    {0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0},
                    {1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                    {1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
                    {1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0},
                    {1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                    {1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0},
                    {1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                    {1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            };

    private Helper(){}

    public static BitSet byteToBitSet(byte b) {
        BitSet bitSet = new BitSet(8);
        String byteString = byteToBinaryString(b);
        for (int i = 0; i < byteString.length(); i++) {
            bitSet.set(i, byteString.charAt(i) == 49);
        }
        return bitSet;
    }

    public static byte bitSetToByte(BitSet bitSet) {
        byte b = 0;
        for (int i = 0; i < 8; i++) {
            if (bitSet.get(i)) {
                b |= (byte) (1 << (7 - i));
            }
        }
        return b;
    }

    public static String byteToBinaryString(byte b) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            binaryString.append((b >> i) & 1);
        }
        return binaryString.toString();
    }

    public static byte[] switchBit(byte[] bytes) {
        BitSet bits = BitSet.valueOf(bytes);
        int place = r.nextInt(bytes.length * 8);
        bits.flip(place);

        return bits.toByteArray();
    }
}
