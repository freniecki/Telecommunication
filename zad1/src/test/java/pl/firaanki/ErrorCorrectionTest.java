package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

class ErrorCorrectionTest {

    @Test
    void multiplyMatrixByVector() {
        ErrorCorrection ec = new ErrorCorrection();
        BitSet testDouble = Helper.byteToBitSet((byte) 79);

        BitSet product = ec.multiplyMatrixByVector(testDouble, Helper.doubleErrorMatrix);
        Assertions.assertEquals("{0, 2, 3}", product.toString());
    }

    @Test
    void codeWord() {
        ErrorCorrection ec = new ErrorCorrection();
        BitSet product = ec.codeWord((byte) 79, Helper.doubleErrorMatrix);
        Assertions.assertEquals("{1, 4, 5, 6, 7, 8, 10, 11}", product.toString());
    }

    @Test
    void codeBytes() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] array = new byte[1];
        array[0] = (byte) 79;

        printBytes(array, "array");
        byte[] encoded = {(byte) 79, (byte) 176};

        byte[] product = ec.codeBytes(array, Helper.doubleErrorMatrix);
        printBytes(product, "product");

        Assertions.assertArrayEquals(encoded, product);
    }

    @Test
    void decodeWord() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] input = {(byte) 51};

        BitSet encodedWord = ec.codeWord((byte) 51, Helper.doubleErrorMatrix);

        byte decoded = ec.decodeWord(encodedWord, Helper.doubleErrorMatrix);
        byte[] output = {decoded};

        printBytes(input, "input");
        printBytes(output, "output");

        Assertions.assertArrayEquals(input, output);
    }

    @Test
    void testFlip() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] input = new byte[]{(byte) 51};
        printBytes(input, "input");

        BitSet encodedWord = ec.codeWord(input[0], Helper.doubleErrorMatrix);
        printBytes(encodedWord.toByteArray(), "encoded");
        encodedWord.flip(4);
        byte[] flipped = encodedWord.toByteArray();
        printBytes(flipped, "flipped");

        // ----------------------------------

        byte[] decodedFlip = ec.decodeBytes(flipped, Helper.doubleErrorMatrix);
        printBytes(decodedFlip, "decodedFlip");

        Assertions.assertArrayEquals(input, decodedFlip);
    }

    @Test
    void decodeByte() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] bytes = {(byte) 79};
        printBytes(bytes, "enter");

        byte[] encoded = ec.codeBytes(bytes, Helper.doubleErrorMatrix);
        printBytes(encoded, "encoded");

        byte[] decoded = ec.decodeBytes(encoded, Helper.doubleErrorMatrix);
        printBytes(decoded, "decoded");

        Assertions.assertArrayEquals(bytes, decoded);
    }

    @Test
    void decodeBytes() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] bytes = {
                (byte) 48, (byte) 57, (byte) 56, (byte) 55, (byte) 54,
                (byte) 53, (byte) 52, (byte) 51, (byte) 50, (byte) 49,
        };
        printBytes(bytes, "enter");

        byte[] encoded = ec.codeBytes(bytes, Helper.doubleErrorMatrix);
        printBytes(encoded,"encoded");

        byte[] decoded = ec.decodeBytes(encoded, Helper.doubleErrorMatrix);
        printBytes(decoded, "decoded");

        Assertions.assertArrayEquals(bytes, decoded);
    }

    static void printBytes(byte[] bytes, String name) {
        System.out.println("---" + name + "---");
        for (byte b : bytes) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("----------------");

    }
}