package pl.firaanki;

import java.util.BitSet;

public class Helper {

    static int[][] singleErrorMatrix =
            {
                    {0, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0},
                    {1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 0, 0},
                    {1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 0},
                    {1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 1},
            };

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
    /**
     * Return clean string of given bits count
     *
     * @param bitSet BitSet object
     * @param length Number of bits to be returned
     * @return Clean string with '0' and '1'
     */
    public static String bitsToString(BitSet bitSet, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (bitSet.get(i)) {
                stringBuilder.append("1");
            } else {
                stringBuilder.append("0");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * Creates BitSet base on string
     *
     * @param plainText key or message given in String
     * @return BitSet(64)
     */
    public static BitSet convertStringToBitSet(String plainText, int length) {
        BitSet output = new BitSet(length);
        for (int i = 0; i < length; i++) {
            output.set(i, plainText.charAt(i) == '1');
        }
        return output;
    }

    /**
     *
     * @param bitSet
     * @param byteSize
     * @return
     */
    public static byte[] bitSetToByteArray(BitSet bitSet, int byteSize) {
        byte[] byteArray = new byte[byteSize];

        for (int i = 0; i < byteSize * 8; i++) {
            if (bitSet.get(i)) {
                byteArray[7 - (i / 8)] |= (byte) (1 << (i % 8));
            }
        }

        return byteArray;
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
        byte b = 0;
        for (int i = 0; i < 8; i++) {
            if (bitSet.get(i)) {
                b |= (byte) (1 << (7 - i));
            }
        }
        return b;
    }

    public static BitSet byteArrayToBitSet(byte[] bytes) {
        BitSet bitSet = new BitSet(bytes.length * 8);
        for (int i = 0; i < bytes.length; i++) {
            BitSet byteBits = byteToBitSet(bytes[i]);
            for (int j = 0; j < 8; j++) {
                bitSet.set(i * 8 + j, byteBits.get(j));
            }
            byteBits.clear();
        }
        return bitSet;
    }

    /**
     * Converts byte to binary representation in string.
     * @param b byte
     * @return binary string
     */
    public static String byteToBinaryString(byte b) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 7; i >= 0; i--) {
            binaryString.append((b >> i) & 1);
        }
        return binaryString.toString();
    }

    public static byte[] toByteArray(BitSet bitSet, int byteSize) {
        byte[] bytes = new byte[byteSize];
        BitSet byteBit = new BitSet(8);
        for (int i = 0; i < byteSize; i++) {
            for (int j = 0; j < 8; j++) {
                byteBit.set(j, bitSet.get(i * 8 + j));
            }
            bytes[i] = Helper.bitSetToByte(byteBit);
            byteBit.clear();
        }
        return bytes;
    }
}
