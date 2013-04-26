package ch.hsr.se2p.snowwars.network.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.ConnectException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.model.Throw;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.RMIServerInterface;

public class RunRMIClient{

	private final static Logger logger = Logger.getLogger(RunRMIClient.class.getPackage().getName());

	private final SnowWarsConfig snowWarsConfig;
	private final SnowWarsClient snowWarsClient;

	private boolean connectedToServer = false;
	private boolean connectedToSnowWars = false;
	
	RMIServerInterface server;
	RMIClientInterface clientStub;

	public RunRMIClient(SnowWarsClient snowWarsClient) {
		this.snowWarsClient = snowWarsClient;
		this.snowWarsConfig = snowWarsClient.getClientConfig();
		System.setProperty("java.security.policy", "rmi.policy");
		try {
		    System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
		} catch (UnknownHostException e) {
		    e.printStackTrace();
		}
        if (System.getSecurityManager() == null)
        {
            System.setSecurityManager(new RMISecurityManager());
        }

	}

	public void connectToServer() throws SnowWarsRMIException {
		try {
		    System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
			Registry serverRegistry = LocateRegistry.getRegistry(snowWarsConfig.getHostname(), snowWarsConfig.getRmiRegistryPort());

			RMIClientInterface client = new RMIClient(this);
			clientStub = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);

			// Remote Objekt (Stub)
			server = (RMIServerInterface) serverRegistry.lookup(snowWarsConfig.getServerRMILookupName());
			logger.info("Successfully Connected to SnowWars-Server");
			connectedToServer = true;
		} catch (Exception e1) {
			throw new SnowWarsRMIException(e1.getMessage());
		}
	}

	public void joinSnowWar() throws SnowWarsRMIException {
		if (connectedToServer && !connectedToSnowWars) {
			logger.info("Joining SnowWar Distributor-Channel...");
			try {
				if (server.registerClient(clientStub)) {
					logger.info("Successfully registered Client on Server");
					connectedToSnowWars = true;
				}
			} catch (RemoteException e) {
				throw new SnowWarsRMIException(e.getMessage());
			}
		}
	}

	public void leaveSnowWar() throws SnowWarsRMIException {
		if (connectedToServer && connectedToSnowWars) {
			logger.info("Leaving SnowWar Distributor-Channel...");
			try {
				if (server.deregisterClient(clientStub)) {
					logger.info("Successfully deregistered Client on Server");
					connectedToSnowWars = false;
				}
			} catch (RemoteException e) {
				throw new SnowWarsRMIException(e.getMessage());
			}
		}
	}

	public void sendThrow(Throw shot) throws SnowWarsRMIException {
		if (connectedToServer && connectedToSnowWars) {
			logger.info("Throwing Shot to Server: " + shot.toString());
			try {
				server.shotThrowed(shot);
			} catch (ConnectException e1) {
				throw new SnowWarsRMIException(e1.getMessage());
			} catch (RemoteException e) {
				throw new SnowWarsRMIException(e.getMessage());
			}
		} else {
			throw new SnowWarsRMIException("No connection to server!");
		}
	}

	public void receivedShot(Throw shot) {
		snowWarsClient.receivedShotRequest(shot);
	}
}