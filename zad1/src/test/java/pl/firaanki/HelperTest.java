package pl.firaanki;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelperTest {

    @Test
    void byteToBitSet() {
        byte b = (byte) 128;
        System.out.println(Helper.byteToBinaryString(b));
        System.out.println(Helper.byteToBitSet(b));
    }
}