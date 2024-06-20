package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConverterTest {

    @Test
    void encodeToBytes() {
        String binaryString = "00101010" +
                "11101010" +
                "01111010" +
                "00010100" +
                "10011110" +
                "101001";

        byte[] bytes = Converter.encodeToBytes(binaryString);

        for (byte b : bytes) {
            System.out.println(b);
        }
    }

    @Test
    void decodeFromBytes() {
        String binaryString = "00101010" +
                "11101010" +
                "01111010" +
                "00010100" +
                "10011110" +
                "101001";

        byte[] bytes = Converter.encodeToBytes(binaryString);
        String string = Converter.decodeFromBytes(bytes);

        Assertions.assertEquals(binaryString, string);
    }
}