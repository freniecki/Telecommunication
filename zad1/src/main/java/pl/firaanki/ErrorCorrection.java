package pl.firaanki;

import java.util.BitSet;

public class ErrorCorrection {

    /**
     * Gets multiplication product of parity matrix and block word
     *
     * @param matrix  Matrix
     * @param message n-bit word
     * @return n-bit block with set parity bits
     */
    BitSet multiplyMatrixByVector(BitSet message, int[][] matrix) {
        BitSet block = new BitSet(matrix.length);

        for (int i = 0; i < matrix.length; i++) {
            boolean resultRow = false;
            for (int j = 0; j < matrix[i].length; j++) {
                resultRow ^= (getBoolean(matrix[i][j]) && message.get(j));
            }
            block.set(i, resultRow);
        }
        return block;
    }

    boolean getBoolean(int value) {
        return value == 1;
    }

    /**
     * Take 8-bit word and count parity bits, making it 12- or 16-bit
     *
     * @param word   byte word
     * @param matrix int[][]
     * @return BitSet with parity bits
     */
    BitSet codeWord(byte word, int[][] matrix) {
        // word in 8-bit BitSet
        BitSet message = Helper.byteToBitSet(word);
        BitSet parityBits = multiplyMatrixByVector(message, matrix);
        for (int i = 0; i < matrix.length; i++) {
            message.set(8 + i, parityBits.get(i));
        }
        return message;
    }

    /**
     * Encode message using Hamming's method
     *
     * @param bytes  byte array
     * @param matrix int[][]
     * @return encoded byte array
     */
    byte[] codeBytes(byte[] bytes, int[][] matrix) {
        int encodedSize;
        int blockSize;

        if (matrix.length == 4) {
            blockSize = 12;
            encodedSize = (int) Math.ceil((double) (bytes.length * 3 ) / 2);
        } else {
            blockSize = 16;
            encodedSize = bytes.length * 2;
        }

        BitSet encodedBytes = new BitSet(encodedSize);

        for (int i = 0; i < bytes.length; i++) {
            BitSet encodedWord = codeWord(bytes[i], matrix);

            for (int j = 0; j < blockSize; j++) {
                encodedBytes.set(i * encodedSize + j, encodedWord.get(j));
            }
        }

        return Helper.toByteArray(encodedBytes, encodedSize);
    }

    BitSet decodeWord(BitSet block, int[][] matrix) {
        int columns = 8 + matrix.length;
        int rows = matrix.length;

        // error vector is size of matrix row
        BitSet errorVector = multiplyMatrixByVector(block, matrix);

        if (!errorVector.isEmpty()) {
            // find column equal to error vector
            for (int j = 0; j < columns; j++) {
                for (int i = 0; i < rows; i++) {
                    if (getBoolean(matrix[i][j]) != errorVector.get(i)) {
                        block.flip(j);
                        return block;
                    }
                }
            }

            // if not found and rows min. 7 (min. for double error correction)
            if (rows >= 7) {
                for (int col1 = 0; col1 < columns; col1++) {
                    for (int col2 = col1 + 1; col2 < columns; col2++) {

                        for (int i = 0; i < rows; i++) {
                            boolean check = getBoolean(matrix[i][col1]) ^ getBoolean(matrix[i][col2]);
                            if (check != errorVector.get(i)) {
                                block.flip(col1);
                                block.flip(col2);
                                return block;
                            }
                        }
                    }
                }
            }
        }
        return block;
    }

    byte[] decodeByte(byte[] bytes, int[][] matrix) {
        // size of return byte[]
        int decodedSize;
        // size of block in bits (12 or 16)
        int blockSize;

        if (matrix.length == 4) {
            blockSize = 12;
            decodedSize = (int) Math.ceil((double) bytes.length / 2);
        } else {
            blockSize = 16;
            decodedSize = bytes.length / 2;
        }

        BitSet encodedBytes = Helper.byteArrayToBitSet(bytes);
        byte[] decoded = new byte[decodedSize];
        BitSet block = new BitSet(blockSize);

        for (int i = 0; i < decodedSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                block.set(j, encodedBytes.get(i * blockSize + j));
            }
            decoded[i] = Helper.bitSetToByte(decodeWord(block, matrix));
            block.clear();
        }

        return decoded;
    }

}
