package pl.firaanki;

import java.io.*;
import java.util.logging.Logger;
import java.net.*;

public class Connection {

    static Logger logger = Logger.getLogger(Connection.class.getName());

    private Connection() {
    }

    public static void sendTextFile(Socket socket, String fileName) {
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
            logger.severe("Nadawca: jakiś błąd");
        }
    }

    public static void sendObject(Socket socket, String fileName) {
        byte[] bytes;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            bytes = ois.readAllBytes();
        } catch (IOException e) {
            logger.severe("Nadwca: cant read huffman bytes");
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())){
            oos.write(bytes);
            oos.flush();
            logger.info("Nadawca: obiekt wysłany");
        } catch (IOException e) {
            logger.severe("Nadawca: błąd z wysłaniem obiektu");
        }
    }

    public static void receiveTextFile(ServerSocket serverSocket, String fileName) {
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
            logger.severe("Odbiorca: receive error");
        }
    }

    public static void receiveObject(ServerSocket serverSocket, String fileName) {
        try (Socket socket = serverSocket.accept();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            logger.info("Odbiorca: nasłuchuje...");

            Huffman huffman = (Huffman) ois.readObject();
            logger.info("Odbiorca: obiekt odebrany");
            FileHandler.getFile(fileName).write(huffman);
            logger.info("Odbiorca: obiekt zapisany do pliku");

        } catch (IOException e) {
            logger.severe("jakis błąd");
        } catch (ClassNotFoundException e) {
            logger.severe("Odbiorca: nie ma takiej klasy");
        }
    }

}
