package pl.firaanki;

import java.io.*;
import java.util.logging.Logger;
import java.net.*;

public class Connection {

    static Logger logger = Logger.getLogger(Connection.class.getName());

    private Connection() {
    }

    public static void send(Socket socket, String fileName) {
        try (OutputStream os = socket.getOutputStream();
             InputStream fileStream = new FileInputStream(fileName)) {

            logger.info("Nadawca: nawiązano połączenie");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileStream.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
            logger.info("Nadawca: plik wysłany");

        } catch (IOException e) {
            logger.severe("Nadawca: błąd przy wysyłaniu pliku");
        }
    }

    public static void receive(ServerSocket serverSocket, String fileName) {
        try (Socket socket = serverSocket.accept();
             DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
             ByteArrayOutputStream bytes = new ByteArrayOutputStream()) {
            logger.info("Odbiorca: nasłuchuje...");

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                bytes.write(buffer, 0, bytesRead);
            }

            FileHandler.getFile(fileName).write(bytes.toByteArray());
            logger.info("Odbiorca: plik odebrany pomyślnie");

        } catch (IOException e) {
            logger.severe("Odbiorca: błąd przy odbieraniu pliku");
        }
    }

    public static void receiveHuffman(ServerSocket serverSocket, String fileName) {
        try (Socket socket = serverSocket.accept();
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            logger.info("Odbiorca: nasłuchuje...");

            Huffman huffman = (Huffman) in.readObject();
            FileHandler.getFile(fileName).write(huffman);
            logger.info("Odbiorca: obiekt odebrany pomyślnie");

        } catch (IOException e) {
            logger.severe("Odbiorca: błąd przy odbieraniu obiektu: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.severe("Odbiorca: klasa nie znaleziona przy deserializacji: " + e.getMessage());
        }
    }
}
