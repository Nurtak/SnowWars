package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.StartRMIServer;

public class SnowWarsServer {

	private final static Logger logger = Logger.getLogger(SnowWarsServer.class.getPackage().getName());
	private StartRMIServer startRMIServer;

	public SnowWarsServer() {
		logger.info("Starting SnowWars-Server");
		try {
			startRMIServer = new StartRMIServer();
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void shutdown(){
	    startRMIServer.shutdown();
	}
	
}
