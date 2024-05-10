package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.logging.Handler;

import static pl.firaanki.ErrorCorrectionTest.printBytes;

class FileHandlerTest {

    @Test
    void write() {
        byte[] bytes = {
                (byte) 48,(byte) 57,(byte) 56,(byte) 55,(byte) 54,
                (byte) 53, (byte) 52,(byte) 51,(byte) 50,(byte) 49,
        };
        FileHandler fileHandler = FileHandler.getFile("write.txt");
        fileHandler.write(bytes);
    }

    @Test
    void read() {
        FileHandler fileHandler = FileHandler.getFile("plik.txt");
        byte[] bytes = fileHandler.read();
        printBytes(bytes, "read");
    }

    @Test
    void testAll() {
        // read from plain file
        FileHandler fileHandler = FileHandler.getFile("plik.txt");
        byte[] bytes = fileHandler.read();
        printBytes(bytes, "message");

        // encode message from file
        ErrorCorrection ec = new ErrorCorrection();
        byte[] encoded = ec.codeBytes(bytes, Helper.singleErrorMatrix);
        //printBytes(encoded, "encoded");

        // write encoded to another file
        FileHandler encodedFileHandler = FileHandler.getFile("encoded.txt");
        encodedFileHandler.write(encoded);

        // read from encoded file
        byte[] encodedRead = encodedFileHandler.read();
        //printBytes(encodedRead, "readEncoded");

        // decode from encoded file
        byte[] decoded = ec.decodeByte(encodedRead, Helper.singleErrorMatrix);
        printBytes(decoded, "decoded");

        // save to decoded file
        FileHandler decodedFileHandler = FileHandler.getFile("decoded.txt");
        decodedFileHandler.write(decoded);

        Assertions.assertArrayEquals(bytes, decoded);
    }
}