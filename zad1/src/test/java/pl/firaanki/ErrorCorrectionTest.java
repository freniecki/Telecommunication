package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

class ErrorCorrectionTest {

    @Test
    void multiplyMatrixByVector() {
        ErrorCorrection ec = new ErrorCorrection();
        BitSet testDouble = Helper.byteToBitSet((byte) 79);

        BitSet product = ec.multiplyMatrixByVector(testDouble);
        Assertions.assertEquals("{0, 2, 3}", product.toString());
    }

    @Test
    void codeWord() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] array = new byte[1];
        array[0] = (byte) 79;
        printBytes(array, "input");

        BitSet product = ec.codeWord(array[0]);
        byte[] productBytes = product.toByteArray();
        printBytes(productBytes, "encoded");

        //Assertions.assertEquals("{1, 4, 5, 6, 7, 8, 10, 11}", product.toString());
    }

    @Test
    void codeBytes() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] array = new byte[1];
        array[0] = (byte) 79;

        printBytes(array, "array");
        byte[] encoded = {(byte) 79, (byte) 176};

        byte[] product = ec.codeBytes(array);
        printBytes(product, "product");

        Assertions.assertEquals(encoded.length, product.length);

        Assertions.assertArrayEquals(encoded, product);
    }

    @Test
    void decodeWord() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] input = {(byte) 51};
        printBytes(input, "input");

        BitSet encodedWord = ec.codeWord(input[0]);
        printBytes(encodedWord.toByteArray(), "encoded");

        byte decoded = ec.decodeWord(encodedWord);
        byte[] output = {decoded};
        printBytes(output, "output");

        Assertions.assertArrayEquals(input, output);
    }

    @Test
    void decodeBytes() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] bytes = {
                (byte) 48, (byte) 57, (byte) 56, (byte) 55, (byte) 54,
                (byte) 53, (byte) 52, (byte) 51, (byte) 50, (byte) 49,
        };
        printBytes(bytes, "enter");

        byte[] encoded = ec.codeBytes(bytes);
        printBytes(encoded,"encoded");

        byte[] decoded = ec.decodeBytes(encoded);
        printBytes(decoded, "decoded");

        Assertions.assertArrayEquals(bytes, decoded);
    }

    @Test
    void testFlip() {
        ErrorCorrection ec = new ErrorCorrection();
        byte[] input = new byte[]{51};
        printBytes(input, "input");

        byte[] encoded = ec.codeBytes(input);
        printBytes(encoded, "encoded");

        BitSet encodedBits = BitSet.valueOf(encoded);
        encodedBits.flip(12);
        encodedBits.flip(13);

        byte[] flipped = encodedBits.toByteArray();
        printBytes(flipped, "flipped");

        byte[] decoded = ec.decodeBytes(flipped);
        printBytes(decoded, "decoded");

        Assertions.assertArrayEquals(input, decoded);
    }

    static void printBytes(byte[] bytes, String name) {
        System.out.println("---" + name + "---");
        for (byte b : bytes) {
            System.out.println(Helper.byteToBinaryString(b));
        }
        System.out.println("-------------");

    }
}