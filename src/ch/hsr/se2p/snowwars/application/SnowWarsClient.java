package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.view.game.ViewGame;
import ch.hsr.se2p.snowwars.view.game.ViewGameController;

public class SnowWarsClient {

	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());

	private SnowWarsConfig clientConfig;

	private ViewGameController viewGameController;
	
	public SnowWarsClient() {
	}

	public void startProgram() {
		logger.info("Starting SnowWars-Client");

		initializeRMIClient();
		initializeGui();
	}

	private void initializeRMIClient(){
		try {
			RunRMIClient runRMIClient = new RunRMIClient(this);
			runRMIClient.connectToServer();
			runRMIClient.joinSnowWar();

		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage());
		}
	}
	
	private void initializeGui() {
		viewGameController = new ViewGameController();
		new ViewGame(viewGameController);
	}
	
	public SnowWarsConfig getClientConfig() {
		if (clientConfig == null) {
			logger.info("Reading configfile...");
			clientConfig = new ConfigLoader().readConfigFile();
		}
		return clientConfig;
	}
}