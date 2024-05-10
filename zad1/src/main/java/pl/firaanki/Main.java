package pl.firaanki;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    static ErrorCorrection ec = new ErrorCorrection();
    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);

        if (args.length != 4) {
            String info = """ 
                    encode/decode inputFileName outputFileName errorCount
                    """;
            logger.info(info);
            return;
        }

        int[][] matrix;

        switch (args[3]) {
            case "1":
                matrix = Helper.singleErrorMatrix;
                break;
            case "2":
                matrix = Helper.doubleErrorMatrix;
                break;
            default:
                logger.info("choose 1 or 2 error correction");
                return;
        }

        switch (args[0]) {
            case "encode":
                runEncode(args[1], args[2], matrix);
                break;
            case "decode":
                runDecode(args[1], args[2], matrix);
                break;
            default:
                logger.info("use proper command");
        }
    }

    private static void runEncode(String inputFileName, String outputFileName, int[][] matrix) {
        FileHandler reader = FileHandler.getFile(inputFileName);
        byte[] bytes = reader.read();

        FileHandler writer = FileHandler.getFile(outputFileName);
        writer.write(ec.codeBytes(bytes, matrix));
    }

    private static void runDecode(String inputFileName, String outputFileName, int[][] matrix) {
        FileHandler reader = FileHandler.getFile(inputFileName);
        byte[] bytes = reader.read();

        FileHandler writer = FileHandler.getFile(outputFileName);
        writer.write(ec.decodeBytes(bytes, matrix));
    }
}
