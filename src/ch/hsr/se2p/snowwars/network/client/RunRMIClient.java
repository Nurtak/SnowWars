package ch.hsr.se2p.snowwars.network.client;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.RMIServerInterface;

public class RunRMIClient {
	
	private final static Logger logger = Logger.getLogger(RunRMIClient.class.getPackage().getName());

	private final SnowWarsConfig snowWarsConfig;
	
	RMIServerInterface server;
	RMIClientInterface clientStub;
	
	public RunRMIClient(SnowWarsClient snowWarsClient) {
		this.snowWarsConfig = snowWarsClient.getClientConfig();
	}

	public void connect() throws SnowWarsRMIException {
		try {
			Registry serverRegistry = LocateRegistry.getRegistry(snowWarsConfig.getServerHostname(), snowWarsConfig.getRmiRegistryPort());

			RMIClientInterface client = new RMIClient();
			clientStub = (RMIClientInterface)UnicastRemoteObject.exportObject(client, 0);

			// Remote Objekt (Stub)
			server = (RMIServerInterface) serverRegistry.lookup(snowWarsConfig.getServerRMILookupName());
			logger.info("Successfully Connected to SnowWars-Server");
		} catch (Exception e1) {
			throw new SnowWarsRMIException(e1.getMessage());
		}
	}
	
	public void joinSnowWar(){
		logger.info("Joining SnowWar Distributor-Channel...");
		try {
			if(server.registerClient(clientStub)){
				logger.info("Successfully registered Client on Server");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void leaveSnowWar(){
		logger.info("Leaving SnowWar Distributor-Channel...");
		try{
			if(server.deregisterClient(clientStub)){
				logger.info("Successfully deregistered Client on Server");
			}
		} catch (RemoteException e){
			e.printStackTrace();
		}
	}
	
	public void throwShot(Shot shot){
		logger.info("Throwing Shot to Server: " + shot.toString());
		try {
			server.shotThrowed(shot);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}