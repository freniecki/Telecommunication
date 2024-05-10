package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

class ErrorCorrectionTest {

    @Test
    void multiplyMatrixByVector() {
        ErrorCorrection ec = new ErrorCorrection();
        BitSet message = Helper.byteToBitSet((byte) 1);
        BitSet block = ec.multiplyMatrixByVector(message, Helper.singleErrorMatrix);
        Assertions.assertEquals("{1, 2}", block.toString());

        // ----------------

        BitSet testBits = Helper.byteToBitSet((byte) 79);
        BitSet test = ec.multiplyMatrixByVector(testBits, Helper.singleErrorMatrix);
        Assertions.assertEquals("{0, 2, 3}", test.toString());

        // ----------------
        BitSet testDouble = Helper.byteToBitSet((byte) 79);
        BitSet product = ec.multiplyMatrixByVector(testDouble, Helper.doubleErrorMatrix);
        Assertions.assertEquals("{0, 2, 3}", product.toString());
    }

    @Test
    void codeWord() {
        ErrorCorrection ec = new ErrorCorrection();

        BitSet productSingle = ec.codeWord((byte) 79, Helper.singleErrorMatrix);
        Assertions.assertEquals("{1, 4, 5, 6, 7, 8, 10, 11}", productSingle.toString());

        // ----------------
        BitSet productDouble = ec.codeWord((byte) 79, Helper.doubleErrorMatrix);
        Assertions.assertEquals("{1, 4, 5, 6, 7, 8, 10, 11}", productDouble.toString());
    }

    @Test
    void codeBytes() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] array = new byte[1];
        array[0] = (byte) 79;
        printBytes(array, "array");

        byte[] encoded = {(byte) 79, (byte) 176};
        byte[] product = ec.codeBytes(array, Helper.singleErrorMatrix);
        printBytes(product, "product");

        byte[] productDouble = ec.codeBytes(array, Helper.doubleErrorMatrix);
        printBytes(productDouble, "productDouble");

        Assertions.assertArrayEquals(encoded, product);
        Assertions.assertArrayEquals(encoded, productDouble);
    }

    @Test
    void decodeWord() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] input = {(byte) 51};
        byte[] encodedBytes = ec.codeBytes(input, Helper.singleErrorMatrix);
        printBytes(encodedBytes, "encoded");

        BitSet encodedWord = new BitSet(12);
        for (int i : new int[]{2, 3, 6, 7, 8, 11}) {
            encodedWord.set(i);
        }

        BitSet decoded = ec.decodeWord(encodedWord, Helper.singleErrorMatrix);
        System.out.println(decoded);
        byte[] output = {Helper.bitSetToByte(decoded)};

        printBytes(input, "input");
        printBytes(output, "output");

        Assertions.assertArrayEquals(input, output);
    }

    @Test
    void decodeByte() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] bytes = {(byte) 79};
        printBytes(bytes, "enter");

        byte[] encoded = ec.codeBytes(bytes, Helper.singleErrorMatrix);
        printBytes(encoded, "encoded");

        byte[] decoded = ec.decodeBytes(encoded, Helper.singleErrorMatrix);
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

        byte[] encoded = ec.codeBytes(bytes, Helper.singleErrorMatrix);
        printBytes(encoded,"encoded");

        byte[] decoded = ec.decodeBytes(encoded, Helper.singleErrorMatrix);
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