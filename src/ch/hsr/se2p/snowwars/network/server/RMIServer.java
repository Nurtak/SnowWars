package ch.hsr.se2p.snowwars.network.server;

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Throw;
import ch.hsr.se2p.snowwars.network.client.RMIClientInterface;

public class RMIServer implements RMIServerInterface{
	
	private final static Logger logger = Logger.getLogger(RMIServer.class.getPackage().getName());
	private ArrayList<RMIClientInterface> clientList = new ArrayList<RMIClientInterface>();
	
	@Override
	public boolean registerClient(RMIClientInterface client) throws RemoteException {
		int activeClientCount = clientList.size()+1;
		logger.info("New Client registered! Active Client-count: " + activeClientCount);
		clientList.add(client);
		return true;
	}

	@Override
	public boolean deregisterClient(RMIClientInterface client) throws RemoteException {
		clientList.remove(client);
		logger.info("Client deregistered.");
		return true;
	}

	@Override
	public boolean shotThrowed(Throw shot) throws RemoteException {
		logger.info("Received Shot: " + shot.toString());
		
		for(RMIClientInterface activeClient : clientList){
			activeClient.shotThrowed(shot);
		}
		return true;
	}
}
