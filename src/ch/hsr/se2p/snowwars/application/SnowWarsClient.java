package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.view.lobby.ViewMain;

public class SnowWarsClient {

    private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());
    private RunRMIClient runRMIClient;
    // private ViewLobbyController viewLobbyController;
    private ViewMain viewMain;

    public void startProgram() {
        logger.info("Starting SnowWars-Client");
        runRMIClient = new RunRMIClient();
        // viewLobbyController = new ViewLobbyController(this);
        viewMain = new ViewMain(this);
    }
    
    public RunRMIClient getRunRMIClient() {
        return runRMIClient;
    }

    public void closeProgram() {
        logger.info("Closing SnowWars-Client");
        System.exit(0);
    }

}
