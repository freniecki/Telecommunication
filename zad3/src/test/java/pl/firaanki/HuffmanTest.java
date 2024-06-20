package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

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
        String expected = "twoja stara w hannowerze";

        Huffman huffman = new Huffman(expected);
        String encoded = huffman.encode();
        byte[] bytes = Converter.encodeToBytes(encoded);
        String decoded = Converter.decodeFromBytes(bytes);

        String product = huffman.decode(decoded);
        System.out.println(product);

        Assertions.assertEquals(expected, product);

    }
}