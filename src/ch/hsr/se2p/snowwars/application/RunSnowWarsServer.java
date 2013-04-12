package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

public class RunSnowWarsServer {
	private final static Logger logger = Logger.getLogger(RunSnowWarsServer.class.getPackage().getName());

	public RunSnowWarsServer() {}
	
	public void startProgram(){
		logger.info("Starting SnowWars-Server");
	}
}
