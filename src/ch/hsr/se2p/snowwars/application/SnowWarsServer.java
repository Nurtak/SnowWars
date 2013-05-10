package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.RunRMIServer;

public class SnowWarsServer {
    
	private final static Logger logger = Logger.getLogger(SnowWarsServer.class.getPackage().getName());
	
	public SnowWarsServer() {
		logger.info("Starting SnowWars-Server");		
		try {
			new RunRMIServer();
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage());
		}
	}
}
