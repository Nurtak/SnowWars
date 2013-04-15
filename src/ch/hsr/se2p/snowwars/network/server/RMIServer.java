package ch.hsr.se2p.snowwars.network.server;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.network.client.RMIClientInterface;

public class RMIServer implements RMIServerInterface{
	
	private final static Logger logger = Logger.getLogger(RMIServer.class.getPackage().getName());

	@Override
	public boolean joinGame(RMIClientInterface client) throws RemoteException {
		logger.info("Invoked joinGame...");
		client.backCall();
		return false;
	}
}
