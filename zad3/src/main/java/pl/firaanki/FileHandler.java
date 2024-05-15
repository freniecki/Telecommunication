package pl.firaanki;

import java.io.*;
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

    public byte[] read() {
        String filePath = new File(fileName).getAbsolutePath();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), US_ASCII))) {
            int character;
            while ((character = reader.read()) != -1) {
                outputStream.write((byte) character);
            }
        } catch (FileNotFoundException e) {
            logger.info("FileNotFoundException");
        } catch (IOException e) {
            logger.info("IOException");
        }

        return outputStream.toByteArray();
    }

    public void write(byte[] data) {
        String filePath = new File(fileName).getAbsolutePath();
        logger.info(filePath);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), US_ASCII))) {
            writer.write(new String(data, US_ASCII));
            logger.info("Data written to file");
        } catch (IOException e) {
            logger.info("Error writing data to file");
        }
    }
}

