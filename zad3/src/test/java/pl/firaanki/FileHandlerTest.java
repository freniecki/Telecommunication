package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class FileHandlerTest {

    @Test
    void readHuffman() {
    }

    @Test
    void write() {
        Huffman huffman = new Huffman("twoja stara");
        String encoded = huffman.encode();
        FileHandler.getFile("huffman").write(huffman);

        Huffman newHuffman = FileHandler.getFile("huffman").readHuffman();


        Assertions.assertEquals("twoja stara", newHuffman.decode(encoded));
    }

    @Test
    void serialization() {
        Huffman huffman = new Huffman("twoja stara");
        String encoded = huffman.encode();

        FileHandler.getFile("huffman").write(huffman);

        Huffman newHuffman = FileHandler.getFile("huffman").readHuffman();

        String decoded = newHuffman.decode(encoded);

        //Assertions.assertSame(huffman, newHuffman);
        Assertions.assertEquals("twoja stara", decoded);
    }
}