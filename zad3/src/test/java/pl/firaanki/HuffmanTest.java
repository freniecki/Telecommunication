package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HuffmanTest {

    @Test
    void encode() {
        Huffman huffman = new Huffman("dupadupa");
        String encoded = huffman.encode();
        Assertions.assertEquals("1001001110010011", encoded);

        String decoded = huffman.decode(encoded);
        Assertions.assertEquals("dupadupa", decoded);
    }

    @Test
    void decode() {
        String encoded = "1001001110010011";
        String expected = "dupadupa";

        Huffman huffman = new Huffman("");

        Assertions.assertEquals(expected, huffman.decode(encoded));
    }
}