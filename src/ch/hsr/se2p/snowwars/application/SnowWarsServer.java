package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;


import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.StartRMIServer;

public class SnowWarsServer {
    
	private final static Logger logger = Logger.getLogger(SnowWarsServer.class.getPackage().getName());
	
	public SnowWarsServer() {
		logger.info("Starting SnowWars-Server");		
		try {
			new StartRMIServer();
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage());
		}
	}
}
