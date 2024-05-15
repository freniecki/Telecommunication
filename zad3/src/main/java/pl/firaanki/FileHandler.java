package pl.firaanki;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class FileHandler {
    private final String fileName;

    static Logger logger = Logger.getLogger(FileHandler.class.getName());

    private FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public static FileHandler getFile(String fileName) {
        return new FileHandler(fileName);
    }

    public String readText() {
        StringBuilder sb = new StringBuilder();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName), US_ASCII)) {
            int character;
            while ((character = reader.read()) != -1) {
                sb.append((char) character);
            }
        } catch (IOException e) {
            logger.info("IOException");
        }

        return sb.toString();
    }

    public Huffman readHuffman() {
        try (ObjectInputStream oi = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Huffman) oi.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.info("readHuffman: error deserializing");
            return null;
        }
    }

    public byte[] readBytes() {
        try {
            return Files.readAllBytes(Paths.get(fileName));
        } catch (IOException e) {
            logger.info("readBytes: IOException");
            return new byte[0];
        }
    }

    public void write(byte[] data) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName), StandardCharsets.US_ASCII)) {
            writer.write(new String(data, US_ASCII));
            logger.info("Data written to file");
        } catch (IOException e) {
            logger.info("Error writing data to file");
        }
    }

    public void write(Huffman huffman) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(huffman);
            logger.info("write Huffman: object written to file");
        } catch (IOException e) {
            logger.info("write Huffman: file not found / IO exception");
        }
    }
}

