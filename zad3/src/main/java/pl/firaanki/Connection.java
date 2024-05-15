package pl.firaanki;

import java.io.*;
import java.util.StringJoiner;
import java.util.logging.Logger;
import java.net.*;

public class Connection {

    static Logger logger = Logger.getLogger(Connection.class.getName());

    private Connection(){}

    public static void connectionSender(int port, String message) {
        final String ADRES_IP = "localhost";

        try (Socket socket = new Socket(ADRES_IP, port)){
            OutputStreamWriter osw = new OutputStreamWriter(socket.getOutputStream());
            osw.write(message);
            osw.flush();
        } catch (IOException e) {
            logger.info("Nadawca: nie nazwiązano połączenia");
        }
    }

    public static void connectionServer(int port) {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Odbiorca: nasłuchuje...");
            Socket socket = serverSocket.accept();
            logger.info("Odbiorca: nawiązano połączenie");

            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            StringBuilder sb = new StringBuilder();

            int bytesRead;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) != -1) {
                sb.append(new String(buffer, 0, bytesRead));
            }
            in.close();

            logger.info(sb.toString());

        } catch (IOException e) {
            logger.info("odbiorca: nie nawiązano połączenia");
        }
    }

}
