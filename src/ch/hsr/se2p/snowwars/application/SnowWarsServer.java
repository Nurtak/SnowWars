package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.config.ServerConfig;

public class SnowWarsServer {
	private final static Logger logger = Logger.getLogger(SnowWarsServer.class.getPackage().getName());

	private ServerConfig serverConfig;
	
	public SnowWarsServer() {}
	
	public void startProgram(){
		logger.info("Starting SnowWars-Server");
	}
	
	public ServerConfig getServerConfig(){
		if(serverConfig == null){
			logger.info("Reading server-configfile...");
			serverConfig = new ConfigLoader().loadServerConfig();
		}
		return serverConfig;
	}
}
