package pl.firaanki;

import java.util.logging.Logger;

public class Main {

    static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        if (args.length != 1 && args.length != 2) {
            String info = """
                    pls, correct command:
                    java -jar java_file_name.jar send text
                    java -jar java_file_name.jar receive
                    """;
            logger.info(info);
        }

        switch (args[0]) {
            case "send":
                Connection.connectionSender(2137, args[1]);
                break;
            case "receive":
                Connection.connectionServer(2137);
                break;
            default:
                String info = """
                    pls, correct command:
                    java -jar java_file_name.jar in/out fileName
                    """;
                logger.info(info);
        }
    }
}
