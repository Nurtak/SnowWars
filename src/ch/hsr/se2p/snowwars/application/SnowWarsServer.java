package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.ConfigFactory;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.RunRMIServer;

public class SnowWarsServer {
	private final static Logger logger = Logger.getLogger(SnowWarsServer.class.getPackage().getName());

	private SnowWarsConfig snowWarsConfig;
	
	public SnowWarsServer() {}
	
	public void startProgram(){
		logger.info("Starting SnowWars-Server");
		
		try {
			new RunRMIServer(this);
		} catch (SnowWarsRMIException e) {
			e.printStackTrace();
		}
	}
	
	public SnowWarsConfig getSnowWarsConfig(){
		if(snowWarsConfig == null){
			logger.info("Reading configfile...");
			snowWarsConfig = ConfigFactory.getSnowWarsConfig();
		}
		return snowWarsConfig;
	}
}
