package ch.hsr.se2p.snowwars.application;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.controller.lobby.ClientLobbyController;
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
        try {
            new ClientLobbyController(this, connectedServerSessionInterface);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
