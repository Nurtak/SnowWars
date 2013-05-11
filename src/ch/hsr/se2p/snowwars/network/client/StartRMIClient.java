package ch.hsr.se2p.snowwars.network.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.Config;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.network.server.RMIServerInterface;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;

public class StartRMIClient extends Observable {

	private final static Logger logger = Logger.getLogger(StartRMIClient.class.getPackage().getName());
	private final Config config;
	private ConnectedServerSessionInterface connectedServerSessionInterface;
	private RMIServerInterface server;

	public StartRMIClient() {
		config = ConfigLoader.getConfig();
		setRMIPropertyAndSecurity();
		setServer();
		setConnectedServerSessionInterface();
	}

	private void setServer() {
		try {
			Registry serverRegistry = LocateRegistry.getRegistry(config.getHostname(), config.getRmiRegistryPort());
			server = (RMIServerInterface) serverRegistry.lookup(config.getServerRMILookupName());
		} catch (RemoteException | NotBoundException e) {
			logger.error(e.getMessage());
		}
	}

	private void setConnectedServerSessionInterface() {
		try {
			connectedServerSessionInterface = server.connect();
			logger.info("Successfully Connected to " + config.getHostname());
		} catch (RemoteException e) {
			logger.error(e.getMessage());
		}
	}

	private void setRMIPropertyAndSecurity() {
		try {
			System.setProperty("java.security.policy", "rmi.policy");
			System.setProperty("java.rmi.system.hostname", InetAddress.getLocalHost().getHostAddress());
			if (System.getSecurityManager() == null) {
				System.setSecurityManager(new RMISecurityManager());
			}
		} catch (UnknownHostException e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * @return the connectedServerSessionInterface
	 */
	public ConnectedServerSessionInterface getConnectedServerSessionInterface() {
		return connectedServerSessionInterface;
	}
}
