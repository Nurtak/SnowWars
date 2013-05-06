package ch.hsr.se2p.snowwars.application;

import java.io.Serializable;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.controller.lobby.ViewLobbyController;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public class SnowWarsClient implements SnowWarsClientInterface, Serializable{

    private static final long serialVersionUID = 4874885087870016147L;
    private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());

    public SnowWarsClient(RunRMIClient runRMIClient) {
        logger.info("Starting SnowWars-Client");
        enterLobby(runRMIClient.getConnectedServerSessionInterface());
    }

    @Override
    public void enterLobby(ConnectedServerSessionInterface connectedServerSessionInterface) {
        new ViewLobbyController(this, connectedServerSessionInterface);
    }

    @Override
    public void enterGame(SnowWarsClientInterface snowWarsClientInterface, GameServerSessionInterface gameServerSessionInterface) {
        // TODO Auto-generated method stub

    }

    public void closeProgram() {
        logger.info("Closing SnowWars-Client");
        System.exit(0);
        // TODO
    }
}
