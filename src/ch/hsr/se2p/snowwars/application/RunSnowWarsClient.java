package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

public class RunSnowWarsClient {

	private final static Logger logger = Logger.getLogger(RunSnowWarsClient.class.getPackage().getName());

	public RunSnowWarsClient() {}
	
	public void startProgram(){
		logger.info("Starting SnowWars-Client");
	}
}