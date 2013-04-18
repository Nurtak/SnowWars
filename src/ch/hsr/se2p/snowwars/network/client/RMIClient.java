package ch.hsr.se2p.snowwars.network.client;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Throw;

public class RMIClient implements RMIClientInterface{

	private final static Logger logger = Logger.getLogger(RMIClient.class.getPackage().getName());

	private RunRMIClient runRMIClient;
	
	public RMIClient(RunRMIClient runRMIClient){
		this.runRMIClient = runRMIClient;
	}
	
	@Override
	public void shotThrowed(Throw shot) {
		logger.info("Received Shot: " + shot.toString());
		runRMIClient.receivedShot(shot);
	}
}