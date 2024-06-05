package pl.firaanki;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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
        printBytes(bytes, "write");
        Assertions.assertTrue(true);
    }

    @Test
    void read() {
        FileHandler fileHandler = FileHandler.getFile("plik.txt");
        byte[] bytes = fileHandler.read();
        printBytes(bytes, "read");
        Assertions.assertTrue(true);

        Charset charset = Charset.defaultCharset();
        System.out.println(charset.toString());
    }

}