package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.ConfigFactory;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.model.Throw;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.view.game.ViewGame;

public class SnowWarsClient {

	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());

	private SnowWarsConfig clientConfig;

	private ViewGameController viewGameController;
	// private ViewLobbyController viewLobbyController;

	RunRMIClient runRMIClient;

	public SnowWarsClient() {
	}

	public void startProgram() {
		logger.info("Starting SnowWars-Client");

		initializeRMIClient();
		initializeGui();
		connectToServer();
	}

	public void closeProgram() {
		logger.info("Closing SnowWars-Client");
		try {
			runRMIClient.leaveSnowWar();
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage());
		}
		System.exit(0);
	}

	private void initializeRMIClient() {
		runRMIClient = new RunRMIClient(this);
	}

	public void connectToServer() {
		try {
			runRMIClient.connectToServer();
			runRMIClient.joinSnowWar();
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage());
			viewGameController.showNoConnectionError();
		}

	}

	private void initializeGui() {
		viewGameController = new ViewGameController(this);
		// viewLobbyController = new ViewLobbyController();

		// new ViewMain(viewLobbyController);
		new ViewGame(viewGameController);
	}

	public SnowWarsConfig getClientConfig() {
		if (clientConfig == null) {
			logger.info("Reading configfile...");
			clientConfig = ConfigFactory.getSnowWarsConfig();
		}
		return clientConfig;
	}

	public void sendShotRequestToServer(Throw shot) {
		try {
			runRMIClient.sendThrow(shot);
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage());
			viewGameController.showNoConnectionError();
		}
	}

	public void receivedShotRequest(Throw shot) {
		viewGameController.receivedThrow(shot);
	}
}