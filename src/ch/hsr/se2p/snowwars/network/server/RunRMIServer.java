package ch.hsr.se2p.snowwars.network.server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsServer;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;

public class RunRMIServer {

	private final static Logger logger = Logger.getLogger(RunRMIServer.class.getPackage().getName());
	
	private final SnowWarsConfig snowWarsConfig;
	
	public RunRMIServer(SnowWarsServer snowWarsServer) throws SnowWarsRMIException{
		snowWarsConfig = snowWarsServer.getSnowWarsConfig();
		initializeRMIService();
	}
	
	private void initializeRMIService() throws SnowWarsRMIException{
		try {
			logger.info("Initializing SnowWars RMI Server...");
			RMIServer rmiServer = new RMIServer();
			
			RMIServerInterface stub;
			stub = (RMIServerInterface) UnicastRemoteObject.exportObject(rmiServer, snowWarsConfig.getRmiRegistryPort());
			
			// Registry
			logger.info("Creating Registry...");
			LocateRegistry.createRegistry(snowWarsConfig.getRmiRegistryPort());
			Registry registry = LocateRegistry.getRegistry();
			
			//Bind Stub
			registry.rebind(snowWarsConfig.getServerRMILookupName(), stub);
			logger.info("SnowWars Server is working...");
		} catch (RemoteException e) {
			throw new SnowWarsRMIException(e.getMessage());
		}
	}
}