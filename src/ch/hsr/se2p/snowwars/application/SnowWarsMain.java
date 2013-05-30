package ch.hsr.se2p.snowwars.application;

import ch.hsr.se2p.snowwars.client.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.server.application.SnowWarsServer;
import ch.hsr.se2p.snowwars.util.logging.Logging;

public class SnowWarsMain {

    public static void main(String[] args) {
        Logging.installLogger();
        if (args.length != 0 && "-server".equalsIgnoreCase(args[0])) {
            new SnowWarsServer();
        } else {
            new SnowWarsClient();
        }
    }
}
