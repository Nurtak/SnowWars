package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.view.lobby.ViewMain;

public class SnowWarsClient {

	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());

	private SnowWarsConfig clientConfig;
	
	public SnowWarsClient() {}
	
	public void startProgram(){
		logger.info("Starting SnowWars-Client");
		
		try {
			new RunRMIClient(this).connect();
		} catch (SnowWarsRMIException e) {
			e.printStackTrace();
		}
		
		new ViewMain(this);
	}
	
	public SnowWarsConfig getClientConfig(){
		if(clientConfig == null){
			logger.info("Reading configfile...");
			clientConfig = new ConfigLoader().readConfigFile();
		}
		return clientConfig;
	}
}