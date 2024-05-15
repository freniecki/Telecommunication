package pl.firaanki;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        String command = """
                    pls, correct command:
                    java -jar java_file_name.jar send text
                    java -jar java_file_name.jar receive
                    """;
        if (args.length != 1 && args.length != 2) {
            logger.info(command);
            return;
        }

        Huffman huffman;
        switch (args[0]) {
            case "send":
                huffman = new Huffman(args[1]);
                String encoded = huffman.encode();

                FileHandler.getFile("serialized").write(huffman);
                FileHandler.getFile("encodedText").write(encoded.getBytes(StandardCharsets.US_ASCII));

                Connection.connectionSender(2137);
                break;
            case "receive":
                Connection.connectionReceiver(2137);

                huffman = FileHandler.getFile("serialized").readHuffman();
                String encodedText = FileHandler.getFile("encodedText").readText();

                String decoded = huffman.decode(encodedText);
                logger.info(decoded);
                break;
            default:
                logger.info(command);
        }
    }
}
