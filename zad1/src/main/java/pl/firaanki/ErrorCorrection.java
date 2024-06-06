package pl.firaanki;

import java.util.BitSet;

public class ErrorCorrection {

    /**
     * Multiplies a bit vector by a matrix and returns the result as a BitSet object.
     * This method is used for calculating parity bits.
     *
     * @param vector The bit vector
     * @return The multiplication result as a BitSet object
     */
    BitSet multiplyMatrixByVector(BitSet vector) {
        int[][] matrix = Helper.doubleErrorMatrix;

        int rows = matrix.length;
        BitSet product = new BitSet(rows);

        for (int i = 0; i < rows; i++) {
            boolean result = false;
            for (int j = 0; j < matrix[i].length; j++) {
                result ^= vector.get(j) && (matrix[i][j] == 1);
            }
            if (result) {
                product.set(i);
            }
        }
        return product;
    }

    /**
     * Encodes an 8-bit word byte using a parity matrix.
     * Returns a BitSet object containing the encoded data (8 word bits + parity bits).
     *
     * @param word The 8-bit word byte to encode
     * @return The encoded data as a BitSet object
     */
    BitSet codeWord(byte word) {
        BitSet message = Helper.byteToBitSet(word);
        BitSet parityBits = multiplyMatrixByVector(message);

        for (int i = 0; i < 8; i++) {
            message.set(i + 8, parityBits.get(i));
        }
        return message;
    }

    /**
     * Encodes an array of bytes using the Hamming method.
     * Returns the encoded byte array with added parity bits.
     *
     * @param bytes The byte array to encode
     * @return The encoded byte array
     */
    byte[] codeBytes(byte[] bytes) {
        int encodedSize = bytes.length * 2;

        BitSet encoded = new BitSet(encodedSize);

        for (int i = 0; i < bytes.length; i++) {
            BitSet block = codeWord(bytes[i]); // returns 16bit word+parity

            for (int j = 0; j < 16; j++) {
                encoded.set(i * 16 + j, block.get(j)); // appends to encoded
            }
        }

        return encoded.toByteArray();
    }

    /**
     * Decodes a 16-bit block of data with parity bits.
     * Detects and corrects a single error if present.
     * Detects and corrects a double error if present.
     * Returns the decoded 8-bit byte.
     *
     * @param block The 16-bit block of data with parity bits
     * @return The decoded 8-bit byte
     */
    byte decodeWord(BitSet block) {
        int[][] matrix = Helper.doubleErrorMatrix;
        int columns = matrix[0].length;
        int rows = matrix.length;
        boolean match = true;

        BitSet errorVector = multiplyMatrixByVector(block);

        if (!errorVector.isEmpty()) {
            // find column equal to error vector
            for (int j = 0; j < columns; j++) {
                match = true;
                for (int i = 0; i < rows; i++) {
                    if ((matrix[i][j] == 1) != errorVector.get(i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    block.flip(j);
                    return Helper.bitSetToByte(block);
                }
            }

            if (!match) {
                return checkDoubleError(block, errorVector);
            }
        }

        return Helper.bitSetToByte(block);
    }

    private byte checkDoubleError(BitSet block, BitSet errorVector) {
        int[][] matrix = Helper.doubleErrorMatrix;
        for (int col1 = 0; col1 < 16; col1++) {
            for (int col2 = col1 + 1; col2 < 16; col2++) {
                boolean match = true;
                for (int i = 0; i < 8; i++) {
                    boolean check = (matrix[i][col1] + matrix[i][col2]) % 2 == 1;
                    if (check != errorVector.get(i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    block.flip(col1);
                    block.flip(col2);
                    return Helper.bitSetToByte(block);
                }
            }
        }
        return Helper.bitSetToByte(block);
    }

    /**
     * Decodes given byte array to smaller byte array with corrected 1 or 2 errors if needed.
     * @param bytes Byte array containing message with parity bits for every block.
     * @return Byte array containing plain message.
     */
    byte[] decodeBytes(byte[] bytes) {
        int decodedSize = bytes.length / 2;
        byte[] decoded = new byte[decodedSize];
        BitSet encoded = BitSet.valueOf(bytes);

        for (int i = 0; i < decodedSize; i++) {
            BitSet block = new BitSet(16);
            for (int j = 0; j < 16; j++) {
                if (encoded.get(i * 16 + j)) {
                    block.set(j);
                }
            }
            decoded[i] = decodeWord(block);
        }

        return decoded;
    }
}
