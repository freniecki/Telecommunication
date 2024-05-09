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
    }

    @Test
    void decodeWord() {
        ErrorCorrection ec = new ErrorCorrection();


    }

    @Test
    void decodeByte() {
        byte[] bytes = {(byte) 79, (byte) 176};

    }
}