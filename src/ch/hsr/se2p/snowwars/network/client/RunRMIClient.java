package ch.hsr.se2p.snowwars.network.client;

import java.rmi.ConnectException;
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
	private final SnowWarsClient snowWarsClient;

	RMIServerInterface server;
	RMIClientInterface clientStub;

	public RunRMIClient(SnowWarsClient snowWarsClient) {
		this.snowWarsClient = snowWarsClient;
		this.snowWarsConfig = snowWarsClient.getClientConfig();
	}

	public void connectToServer() throws SnowWarsRMIException {
		try {
			Registry serverRegistry = LocateRegistry.getRegistry(snowWarsConfig.getHostname(), snowWarsConfig.getRmiRegistryPort());

			RMIClientInterface client = new RMIClient(this);
			clientStub = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);

			// Remote Objekt (Stub)
			server = (RMIServerInterface) serverRegistry.lookup(snowWarsConfig.getServerRMILookupName());
			logger.info("Successfully Connected to SnowWars-Server");
		} catch (Exception e1) {
			throw new SnowWarsRMIException(e1.getMessage());
		}
	}

	public void joinSnowWar() throws SnowWarsRMIException {
		logger.info("Joining SnowWar Distributor-Channel...");
		try {
			if (server.registerClient(clientStub)) {
				logger.info("Successfully registered Client on Server");
			}
		} catch (RemoteException e) {
			throw new SnowWarsRMIException(e.getMessage());
		}
	}

	public void leaveSnowWar() throws SnowWarsRMIException {
		logger.info("Leaving SnowWar Distributor-Channel...");
		try {
			if (server.deregisterClient(clientStub)) {
				logger.info("Successfully deregistered Client on Server");
			}
		} catch (RemoteException e) {
			throw new SnowWarsRMIException(e.getMessage());
		}
	}

	public void sendShot(Shot shot) throws SnowWarsRMIException {
		if (server != null) {
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

	public void receivedShot(Shot shot) {
		snowWarsClient.receivedShotRequest(shot);
	}
}