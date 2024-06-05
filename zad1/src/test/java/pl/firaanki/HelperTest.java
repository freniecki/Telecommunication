package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.BitSet;

class HelperTest {

    @Test
    void byteToBitSet() {
        byte b = (byte) 128;
        Assertions.assertEquals("{0}", Helper.byteToBitSet(b).toString());
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
}