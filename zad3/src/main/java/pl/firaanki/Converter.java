package pl.firaanki;

public class Converter {

    private Converter(){}

    public static byte[] encodeToBytes(String binaryString) {
        int length = (int) Math.ceil((double) binaryString.length() / 8);
        byte[] bytes = new byte[length + 1];

        for (int i = 0; i < binaryString.length(); i++) {
            if (binaryString.charAt(i) == '1') {
                bytes[i / 8] |= (byte) (1 << (7 - (i % 8)));
            }
        }

        int paddingLength = 8 - binaryString.length() % 8;
        if (paddingLength != 8) {
            bytes[length] = (byte) paddingLength;
        }

        return bytes;
    }

    public static String decodeFromBytes(byte[] bytes) {
        int paddingLength = bytes[bytes.length - 1];

        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < (bytes.length - 1) * 8 - paddingLength; i++) {
            byte check = (byte) ((1 << (7 - i % 8)) & bytes[i / 8]);

            if (check == (byte) (1 << (7 - (i % 8)))) {
                binaryString.append("1");
            } else {
                binaryString.append("0");
            }
        }
        // cut padding
        return binaryString.toString();
    }
}
