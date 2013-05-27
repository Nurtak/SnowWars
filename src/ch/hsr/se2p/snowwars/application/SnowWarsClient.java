package ch.hsr.se2p.snowwars.application;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.network.client.StartRMIClient;
import ch.hsr.se2p.snowwars.network.server.session.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.server.session.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.view.game.controlling.ViewGameController;
import ch.hsr.se2p.snowwars.view.lobby.controlling.ClientLobbyController;
import ch.hsr.se2p.snowwars.view.lobby.presentation.ClientViewMain;

public class SnowWarsClient implements SnowWarsClientInterface {
	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());
	private ClientLobbyController clientLobbyController;

	public SnowWarsClient() {
		startProgram();
	}

	@Override
	public void startProgram() {
		logger.info("Starting new SnowWars-Client");
		enterLobby(new StartRMIClient().getConnectedServerSessionInterface());
	}

	@Override
	public void enterLobby(ConnectedServerSessionInterface connectedServerSessionInterface) {
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
