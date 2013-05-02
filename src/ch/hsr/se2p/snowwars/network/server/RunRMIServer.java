package ch.hsr.se2p.snowwars.network.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RMISecurityManager;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsServer;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;

public class RunRMIServer {

	private final static Logger logger = Logger.getLogger(RunRMIServer.class.getPackage().getName());

	private final SnowWarsConfig snowWarsConfig;

	public RunRMIServer(SnowWarsServer snowWarsServer) throws SnowWarsRMIException {
		snowWarsConfig = snowWarsServer.getSnowWarsConfig();
		initializeRMIService();
	}

	private void initializeRMIService() throws SnowWarsRMIException {
		try {
			logger.info("Initializing SnowWars RMI Server...");
			System.setProperty("java.security.policy", "rmi.policy");

			try {
				System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}
			Lobby lobby = new Lobby();
			RMIServer rmiServer = new RMIServer(lobby);

			RMIServerInterface stub;
			stub = (RMIServerInterface) UnicastRemoteObject.exportObject(rmiServer, 0);

			// Registry
			logger.info("Creating Registry...");
			LocateRegistry.createRegistry(snowWarsConfig.getRmiRegistryPort());
			Registry registry = LocateRegistry.getRegistry();

			// Bind Stub
			registry.rebind(snowWarsConfig.getServerRMILookupName(), stub);
			logger.info("SnowWars Server is working...");
		} catch (Exception e) {
			throw new SnowWarsRMIException(e.getMessage());
		}
	}
}