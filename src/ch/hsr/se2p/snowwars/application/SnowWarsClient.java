package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;

public class SnowWarsClient {

	private final static Logger logger = Logger.getLogger(SnowWarsClient.class.getPackage().getName());

	private SnowWarsConfig clientConfig;
	
	public SnowWarsClient() {}
	
	public void startProgram(){
		logger.info("Starting SnowWars-Client");
		
		try {
			RunRMIClient rmiClient = new RunRMIClient(this);
			rmiClient.connect();
			rmiClient.joinSnowWar();
			
			//starting throwthread (thread who asks user to throw a shot)
			ThrowThread throwThread = new ThrowThread(rmiClient);
			throwThread.start();
			throwThread.join();
			rmiClient.leaveSnowWar();
		} catch (SnowWarsRMIException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//new ViewMain(this);
	}
	
	public SnowWarsConfig getClientConfig(){
		if(clientConfig == null){
			logger.info("Reading configfile...");
			clientConfig = new ConfigLoader().readConfigFile();
		}
		return clientConfig;
	}
}