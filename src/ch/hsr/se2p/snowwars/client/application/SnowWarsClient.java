package ch.hsr.se2p.snowwars.client.application;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.client.game.applicationcontrolling.GameClient;
import ch.hsr.se2p.snowwars.client.game.applicationcontrolling.ViewGameController;
import ch.hsr.se2p.snowwars.client.lobby.applicationcontrolling.ClientLobbyController;
import ch.hsr.se2p.snowwars.client.lobby.view.ClientViewMain;
import ch.hsr.se2p.snowwars.client.network.StartRMIClient;
import ch.hsr.se2p.snowwars.network.serversession.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.serversession.GameServerSessionInterface;

public class SnowWarsClient implements SnowWarsClientInterface {
	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());
	private ClientLobbyController clientLobbyController;
	private ConnectedServerSessionInterface connectedServerSessionInterface;

	public SnowWarsClient() {
	    logger.info("Starting new SnowWars-Client");
	    connectedServerSessionInterface = new StartRMIClient().getConnectedServerSessionInterface();
		enterLobby();
	}
	
	@Override
	public void enterLobby() {
		try {
			clientLobbyController = new ClientLobbyController(this, connectedServerSessionInterface);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void closeProgram() {
		logger.info("Closing SnowWars-Client");
		System.exit(0);
	}

	@Override
	public void enterGame(final GameServerSessionInterface gameServerSessionInterface) {
		try {
			ViewGameController viewGameController = new ViewGameController(this, new GameClient(gameServerSessionInterface));
			gameServerSessionInterface.setGameClientSessionInterface(viewGameController);
			
			viewGameController.showGui();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
    /**
     * ONLY FOR TESTING!
     * @return the clientViewMain
     */
    public ClientViewMain getClientViewMain() {
        return clientLobbyController.getClientViewMain();
    }
}
