package ch.hsr.se2p.snowwars.application;

import java.io.Serializable;
import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.controller.lobby.ClientLobbyController;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;

public class SnowWarsClient implements SnowWarsClientInterface, Serializable {

	private static final long serialVersionUID = 4874885087870016147L;
	private final static Logger logger = Logger.getLogger(SnowWarsClient.class
			.getPackage().getName());

	public SnowWarsClient() {
		logger.info("Starting SnowWars-Client");
		enterLobby(new RunRMIClient().getConnectedServerSessionInterface());
	}

	@Override
	public void enterLobby(
			ConnectedServerSessionInterface connectedServerSessionInterface) {
		try {
			new ClientLobbyController(this, connectedServerSessionInterface);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void closeProgram() {
		logger.info("Closing SnowWars-Client");
		System.exit(0);
	}

	@Override
	public void enterGame(ViewGameController viewGameController) {
		viewGameController.showGui();
	}
}
