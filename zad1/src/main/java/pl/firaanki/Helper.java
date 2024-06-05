package pl.firaanki;

import java.util.BitSet;

public class Helper {

    static int[][] doubleErrorMatrix =
            {
                    {1, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0},
                    {0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0},
                    {0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0},
                    {1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0},
                    {0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                    {1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                    {1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0},
                    {0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1}
            };

    private Helper() {
    }

    public static BitSet byteToBitSet(byte b) {
        BitSet bitSet = new BitSet(8);
        String byteString = byteToBinaryString(b);
        for (int i = 0; i < byteString.length(); i++) {
            bitSet.set(i, byteString.charAt(i) == 49);
        }
        return bitSet;
    }

    public static byte bitSetToByte(BitSet bitSet) {
        byte result = 0;
        int bit = 0;
        for (int i = 7; i >= 0; i--) {
            if (bitSet.get(bit)) {
                result |= (byte) (1 << i);
            }
            bit++;
        }
        return result;
    }

    public static String byteToBinaryString(byte b) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            binaryString.append((b >> i) & 1);
        }
        return binaryString.toString();
    }
}
