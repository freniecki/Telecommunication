package pl.firaanki;

import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class Main {
    static Logger logger = Logger.getLogger(Main.class.getName());

    static ErrorCorrection ec = new ErrorCorrection();
    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        logger.addHandler(consoleHandler);
        String info = """ 
                    encode/decode inputFileName outputFileName
                    1bit fileName
                    2bit fileName
                    """;

        if (args.length != 2 && args.length != 4) {
            logger.info(info);
            return;
        }
        if (args.length == 2) {
            String fileName = args[1];
            switch (args[0]) {
                case "1bit":
                    switchBit(1, fileName);
                    break;
                case "2bit":
                    switchBit(2, fileName);
                    break;
                default:
                    logger.info(info);
            }
        } else {
            int[][] matrix = Helper.doubleErrorMatrix;

            switch (args[0]) {
                case "encode":
                    runEncode(args[1], args[2], matrix);
                    break;
                case "decode":
                    runDecode(args[1], args[2], matrix);
                    break;
                default:
                    logger.info(info);
            }
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

    private static void switchBit(int bits, String fileName) {
        FileHandler reader = FileHandler.getFile(fileName);
        byte[] bytes = reader.read();
        if (bits == 1) {
            byte[] oneBit = Helper.switchBit(bytes);
            FileHandler.getFile(fileName).write(oneBit);
        } else {
            byte[] twoBits = Helper.switchBit(Helper.switchBit(bytes));
            FileHandler.getFile(fileName).write(twoBits);
        }
    }
}
