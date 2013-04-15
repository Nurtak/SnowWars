package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.ClientConfig;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.view.lobby.ViewMain;

public class RunSnowWarsClient {

	private final static Logger logger = Logger.getLogger(RunSnowWarsClient.class.getPackage().getName());

	private ClientConfig clientConfig;
	
	public RunSnowWarsClient() {}
	
	public void startProgram(){
		logger.info("Starting SnowWars-Client");
		
		new ViewMain(this);
	}
	
	public ClientConfig getClientConfig(){
		if(clientConfig == null){
			logger.info("Reading configfile...");
			clientConfig = new ConfigLoader().loadClientConfig();
		}
		return clientConfig;
	}
}