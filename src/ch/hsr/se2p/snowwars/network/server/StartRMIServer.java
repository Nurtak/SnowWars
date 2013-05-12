package ch.hsr.se2p.snowwars.network.server;

import java.net.InetAddress;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.Config;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.model.Lobby;

public class StartRMIServer {

	private final static Logger logger = Logger.getLogger(StartRMIServer.class.getPackage().getName());
	private final Config config;

	public StartRMIServer() throws SnowWarsRMIException {
		config = ConfigLoader.getConfig();
		setRMIPropertyAndSecurity();
		setRegistryAndStub();
	}

	private void setRMIPropertyAndSecurity() {
		try {
			logger.info("Initializing SnowWars RMI Server...");
			System.setProperty("java.security.policy", "rmi.policy");

			System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());

			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setRegistryAndStub() {
		try {
			Lobby lobby = new Lobby();
			RMIServer rmiServer = new RMIServer(lobby);

			RMIServerInterface stub;

			stub = (RMIServerInterface) UnicastRemoteObject.exportObject(rmiServer, 0);

			// Registry
			logger.info("Creating Registry...");
			LocateRegistry.createRegistry(config.getRmiRegistryPort());
			Registry registry = LocateRegistry.getRegistry();

			// Bind Stub
			registry.rebind(config.getServerRMILookupName(), stub);
			logger.info("SnowWars Server is working...");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}