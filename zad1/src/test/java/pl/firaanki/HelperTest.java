package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.BitSet;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void byteToBitSet() {
        byte b = (byte) 128;
        System.out.println(Helper.byteToBinaryString(b));
        System.out.println(Helper.byteToBitSet(b));
    }

    @Test
    void bitSetToByte() {
        byte b = (byte) 128;
        System.out.println(Helper.byteToBinaryString(b));
        BitSet bitSet = Helper.byteToBitSet(b);
        byte byt = Helper.bitSetToByte(bitSet);
        System.out.println(Helper.byteToBinaryString(byt));

        Assertions.assertEquals(b, byt);
    }

    @Test
    void byteArrayToBitSet() {

    }
}