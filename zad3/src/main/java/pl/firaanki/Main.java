package pl.firaanki;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String command = """
                java -jar java_file_name.jar send/receive
                """;
        String encodedFile = "encoded";
        String huffmanFile = "huffman";
        
        if (args.length != 1) {
            logger.info(command);
            return;
        }
        Scanner scanner = new Scanner(System.in);

        if (args[0].equalsIgnoreCase("send")) {
            sender(scanner, encodedFile, huffmanFile);
        } else {
            receiver(scanner, encodedFile, huffmanFile);
        }
    }

    private static void receiver(Scanner scanner, String encodedFile, String huffmanFile) {
        String menuReceiver = """
                [q] exit
                [t] receive text
                [d] receive dictionary
                [p] print text
                """;

        logger.info("Wprowadź numer gniazda:");
        int port = scanner.nextInt();

        String encoded = null;
        String decoded = null;
        Huffman huffman;

        try (ServerSocket socket = new ServerSocket(port)){
            logger.info("Odbiorca: założono gniazdo");
            String option;
            while (true) {
                logger.info(menuReceiver);
                option = scanner.nextLine();

                switch (option) {
                    case "q":
                        return;
                    case "t":
                        Connection.receiveTextFile(socket, encodedFile);
                        encoded = FileHandler.getFile(encodedFile).readText();
                        break;
                    case "d":
                        Connection.receiveObject(socket, huffmanFile);
                        huffman = FileHandler.getFile(huffmanFile).readHuffman();
                        decoded = huffman.decode(encoded);
                        FileHandler.getFile("text").write(decoded.getBytes());
                        break;
                    case "p":
                        logger.info(Objects.requireNonNullElse(decoded, "Nie odebrano wiadomości bądź słownika"));
                        break;
                    default:
                }
            }

        } catch (IOException e) {
            logger.severe("Odbiorca: nie założono gniazda");
        }
    }

    private static void sender(Scanner scanner, String encodedFile, String huffmanFile) {
        String menuSender = """
                [q] exit
                [p] print text
                [c] print encoded
                [t] send text
                [d] send dictionary
                """;
        
        logger.info("Wprowadź adres IPv4 odbiorcy:");
        String ipv4 = scanner.nextLine();
        logger.info("Wprowadź numer gniazda:");
        int port = Integer.parseInt(scanner.nextLine());
        logger.info("Wprowadź nazwę pliku z tekstem:");
        String textFile = scanner.nextLine();

        String text = FileHandler.getFile(textFile).readText();
        Huffman huffman = new Huffman(text);
        String encoded = huffman.encode();

        FileHandler.getFile(encodedFile).write(encoded.getBytes());
        FileHandler.getFile(huffmanFile).write(huffman);

        String option;
        while (true) {
            logger.info(menuSender);
            option = scanner.nextLine();

            switch (option) {
                case "q":
                    return;
                case "p":
                    logger.info(text);
                    break;
                case "c":
                    logger.info(encoded);
                    break;
                case "t":
                    try (Socket socket = new Socket(ipv4, port)) {
                        Connection.sendTextFile(socket, encodedFile);
                    } catch (IOException e) {
                        logger.severe("Nadawca: nie założono gniazda");
                    }
                    break;
                case "d":
                    try (Socket socket = new Socket(ipv4, port)) {
                        Connection.sendObject(socket, huffmanFile);
                    } catch (IOException e) {
                        logger.severe("Nadawca: nie założono gniazda");
                    }
                    break;
                default:
            }
        }
    }
}
