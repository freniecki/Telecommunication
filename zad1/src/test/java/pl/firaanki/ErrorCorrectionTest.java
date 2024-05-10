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

        byte[] product = ec.codeBytes(array, Helper.singleErrorMatrix);
        System.out.println("----------------");
        for (byte b : array) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("==============");
        for (byte b : product) {
            System.out.println(Helper.byteToBinaryString(b));
        }

        System.out.println();

        byte[] productDouble = ec.codeBytes(array, Helper.doubleErrorMatrix);
        System.out.println("----------------");
        for (byte b : array) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("==============");
        for (byte b : productDouble) {
            System.out.println(Helper.byteToBinaryString(b));
        }

    }

    @Test
    void decodeWord() {
        ErrorCorrection ec = new ErrorCorrection();
        BitSet encoded = new BitSet(12);
        encoded.set(1);
        encoded.set(4);
        encoded.set(5);
        encoded.set(6);
        encoded.set(7);
        encoded.set(8);
        encoded.set(9);
        encoded.set(10);
        encoded.set(11);

        System.out.println(encoded);

        BitSet decoded = ec.decodeWord(encoded,Helper.singleErrorMatrix);
        System.out.println(decoded);
    }

    @Test
    void decodeByte() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] bytes = {(byte) 79};
        printBytes(bytes, "enter");

        byte[] encoded = ec.codeBytes(bytes, Helper.singleErrorMatrix);
        printBytes(encoded,"encoded");

        byte[] decoded = ec.decodeByte(encoded, Helper.singleErrorMatrix);
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