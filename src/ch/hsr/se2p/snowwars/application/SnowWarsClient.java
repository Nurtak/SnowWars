package ch.hsr.se2p.snowwars.application;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.network.client.StartRMIClient;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.view.game.controlling.ViewGameController;
import ch.hsr.se2p.snowwars.view.lobby.controlling.ClientLobbyController;

public class SnowWarsClient implements SnowWarsClientInterface {
	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());

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
			new ClientLobbyController(this, connectedServerSessionInterface);
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
}
