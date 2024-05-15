package pl.firaanki;

import java.io.*;
import java.util.logging.Logger;
import java.net.*;

public class Connection {

    static Logger logger = Logger.getLogger(Connection.class.getName());

    private Connection(){}

    public static void connectionSender(int port) {
        final String ADRES_IP = "localhost";

        writeToFile(ADRES_IP, port, "encodedText");
        writeToFile(ADRES_IP, port, "serialized");
    }

    public static void writeToFile(String address, int port, String fileName) {
        try (Socket socket = new Socket(address, port)) {
            OutputStream os = socket.getOutputStream();
            os.write(FileHandler.getFile(fileName).readBytes());
            os.flush();
            os.close();
        } catch (IOException e) {
            logger.info("Nadawca: nie nazwiązano połączenia");
        }
    }

    public static void connectionReceiver(int port) {
        readFromFile(port, "encodedText");
        readFromFile(port, "serialized");
    }

    private static void readFromFile(int port, String fileName) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Odbiorca: nasłuchuje...");
            Socket serializeSocket = serverSocket.accept();
            logger.info("Odbiorca: nawiązano połączenie");

            DataInputStream in = new DataInputStream(new BufferedInputStream(serializeSocket.getInputStream()));
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) != -1) {
                bytes.write(buffer, 0, bytesRead);
            }
            in.close();

            FileHandler.getFile(fileName).write(bytes.toByteArray());
        } catch (IOException e) {
            logger.info("Odbiorca: nie nawiązano połączenia");
        }
    }

}
