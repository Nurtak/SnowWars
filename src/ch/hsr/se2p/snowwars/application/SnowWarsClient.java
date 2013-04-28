package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.ConfigFactory;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.controller.lobby.ViewLobbyController;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.client.Player;
import ch.hsr.se2p.snowwars.model.client.User;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.PlayerInterface;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.view.game.GameFrame;
import ch.hsr.se2p.snowwars.view.lobby.ViewMain;

public class SnowWarsClient {

	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());

	private SnowWarsConfig clientConfig;

	private ViewGameController viewGameController;
	private ViewLobbyController viewLobbyController;

	private PlayerInterface playerLeft;
	private PlayerInterface playerRight;

	RunRMIClient runRMIClient;

	public SnowWarsClient() {
	}

	public void startProgram(String arg) {
		logger.info("Starting SnowWars-Client");

		initializeRMIClient();
		initializeGui(arg);
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

	private void initializeGui(String arg) {
		if (arg.equals("-g")) {
			logger.info("Starting in Game-Mode...");
			viewGameController = new ViewGameController(this);
			new GameFrame(viewGameController);
		} else if (arg.equals("-l")) {
			logger.info("Starting in Lobby-Mode...");
			viewLobbyController = new ViewLobbyController(this);
			new ViewMain(viewLobbyController);
		}
	}

	public SnowWarsConfig getClientConfig() {
		if (clientConfig == null) {
			logger.info("Reading configfile...");
			clientConfig = ConfigFactory.getSnowWarsConfig();
		}
		return clientConfig;
	}

	public void sendShotRequestToServer(Shot shot) {
		try {
			runRMIClient.sendThrow(shot);
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage());
			viewGameController.showNoConnectionError();
		}
	}

	public void receivedShotRequest(Shot shot) {
		viewGameController.receivedShot(shot);
	}

	public PlayerInterface getPlayerLeft() {
		if (this.playerLeft == null) {
			this.playerLeft = new Player(new User("AbraXus"));
			this.playerLeft.setHitPoints(100);
		}
		return this.playerLeft;
	}

	public PlayerInterface getPlayerRight() {
		if (this.playerRight == null) {
			this.playerRight = new Player(new User("Laktose"));
			this.playerRight.setHitPoints(50);
		}
		return this.playerRight;
	}
}