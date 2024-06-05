package pl.firaanki;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Logger;

public class FileHandler {

    private final String fileName;

    Logger logger = Logger.getLogger(FileHandler.class.getName());

    FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public static FileHandler getFile(String fileName) {
        return new FileHandler(fileName);
    }

    public byte[] read() {
        String filePath = new File(fileName).getAbsolutePath();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filePath))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            logger.info("FileNotFoundException");
        } catch (IOException e) {
            logger.info("IOException");
        }

        return outputStream.toByteArray();
    }

    public void write(byte[] message) {
        String filePath = new File(fileName).getAbsolutePath();
        logger.info(filePath);
        File file = new File(filePath);

        try {
            if (file.exists()) {
                logger.info("file exist");
            } else {
                if (file.createNewFile()) {
                    logger.info("file created");
                } else {
                    logger.info("cannot create file");
                }
            }
        } catch (IOException e) {
            logger.info("error when creating file");
        }

        try (BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(filePath))) {

            writer.write(message);
            logger.info("written to file");
        } catch (IOException e) {
            logger.info("cannot write to file");
        }
    }

    public void writeBytesDefaultCharset(byte[] data, Charset charset) {
        String filePath = new File(fileName).getAbsolutePath();
        logger.info(filePath);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), charset))) {
            writer.write(new String(data, charset));
            logger.info("Data written to file");
        } catch (IOException e) {
            logger.info("Error writing data to file");
        }
    }
}
