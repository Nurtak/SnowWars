package ch.hsr.se2p.snowwars.server.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.server.network.StartRMIServer;
import ch.hsr.se2p.snowwars.util.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.util.logging.Logging;

public class SnowWarsServer {

    private final static Logger logger = Logger.getLogger(SnowWarsServer.class.getPackage().getName());
    private StartRMIServer startRMIServer;

    public SnowWarsServer() {
        logger.info("Starting SnowWars-Server");
        try {
            startRMIServer = new StartRMIServer();
        } catch (SnowWarsRMIException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void shutdown() {
        startRMIServer.shutdown();
    }
    
    public static void main(String[] args) {
        Logging.installLogger();
        new SnowWarsServer();
    }

}
