package pl.firaanki;

import java.util.BitSet;
import java.util.logging.Logger;


public class ErrorCorrection {
    Logger logger = Logger.getLogger(getClass().getName());

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
            logger.info(Helper.bitsToString(encodedWord, blockSize));

            for (int j = 0; j < blockSize; j++) {
                encodedBytes.set(i * encodedSize + j, encodedWord.get(j));
            }
        }
        logger.info(Helper.bitsToString(encodedBytes, encodedSize * 8));
        return Helper.toByteArray(encodedBytes, encodedSize);
    }

    BitSet decodeWord(BitSet block, int[][] matrix, int blockSize) {
        int columns = 8 + matrix.length;
        int rows = matrix.length;

        // todo: to nie jest error vector
        BitSet errorVector = multiplyMatrixByVector(block, matrix);
        boolean correct = true;
        for (int i = 0; i < blockSize; i++) {
            // if error vector non-zero
            if (errorVector.get(i)) {
                correct = false;
                break;
            }
        }
        // single error - bit index
        int errorBitNumber = -1;
        // double error - bits indexes
        int errorBitNumber1 = -1;
        int errorBitNumber2 = -1;

        if (!correct) {
            // find column equal to error vector
            for (int j = 0; j < columns; j++) {
                boolean identical = true;
                for (int i = 0; i < rows; i++) {
                    if (getBoolean(matrix[i][j]) != errorVector.get(i)) {
                        identical = false;
                        break;
                    }
                }
                if (identical) {
                    errorBitNumber = j;
                    break;
                }
            }

            // if not found and rows min. 7 (min. for double error correction)
            if (errorBitNumber == -1 && rows >= 7) {
                for (int j1 = 0; j1 < columns; j1++) {
                    boolean identical = true;
                    for (int j2 = j1 + 1; j2 < columns; j2++) {
                        identical = true;

                        for (int i = 0; i < rows; i++) {
                            boolean check = getBoolean(matrix[i][j1]) ^ getBoolean(matrix[i][j2]);
                            if (check != errorVector.get(i)) {
                                identical = false;
                                break;
                            }
                        }

                        if (identical) {
                            errorBitNumber1 = j1;
                            errorBitNumber2 = j2;
                            break;
                        }
                    }

                    if (identical) {
                        break;
                    }
                }
            }
        }

        if (errorBitNumber != -1){
            // single error correction
            block.flip(errorBitNumber);
        } else if (errorBitNumber1 != -1 && errorBitNumber2 != -1){
            // double error correction
            block.flip(errorBitNumber1);
            block.flip(errorBitNumber2);
        }
        return block;
    }

    byte[] decodeByte(byte[] bytes, int[][] matrix) {
        int decodedSize;
        int blockSize;

        if (matrix.length == 4) {
            blockSize = 12;
            decodedSize = (int) Math.floor((double) (bytes.length * 3 ) / 2);
        } else {
            blockSize = 16;
            decodedSize = bytes.length / 2;
        }
        BitSet encodedBytes = Helper.byteArrayToBitSet(bytes);
        BitSet decodedBytes = new BitSet(decodedSize);

        int primaryByteCount = bytes.length * 8 / blockSize;
        for (int i = 0; i < primaryByteCount; i++) {
            BitSet block = new BitSet(blockSize);
            for (int j = 0; j < blockSize; j++) {
                block.set(j, encodedBytes.get(i * primaryByteCount + j));
            }
            BitSet decodedWord = decodeWord(block, matrix, blockSize);
            for (int j = 0; j < 8; j++) {
                decodedBytes.set(i * 8 + j, decodedWord.get(j));
            }
        }

        return decodedBytes.toByteArray();
    }

}
