package pl.firaanki;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    static ErrorCorrection ec = new ErrorCorrection();
    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);

        Charset charset = StandardCharsets.US_ASCII;
        String info = """ 
                    encode/decode inputFileName outputFileName
                    """;

        if (args.length != 3) {
            logger.info(info);
            return;
        }

        switch (args[0]) {
            case "encode":
                runEncode(args[1], args[2]);
                break;
            case "decode":
                runDecode(args[1], args[2], charset);
                break;
            default:
                logger.info(info);
        }
    }

    private static void runEncode(String inputFileName, String outputFileName) {
        FileHandler reader = FileHandler.getFile(inputFileName);
        byte[] bytes = reader.read();

        FileHandler writer = FileHandler.getFile(outputFileName);
        writer.write(ec.codeBytes(bytes));
    }

    private static void runDecode(String inputFileName, String outputFileName, Charset charset) {
        FileHandler reader = FileHandler.getFile(inputFileName);
        byte[] bytes = reader.read();

        FileHandler writer = FileHandler.getFile(outputFileName);
        writer.writeBytesDefaultCharset(ec.decodeBytes(bytes), charset);
    }
}
