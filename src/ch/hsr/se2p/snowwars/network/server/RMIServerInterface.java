package ch.hsr.se2p.snowwars.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Throw;
import ch.hsr.se2p.snowwars.network.client.RMIClientInterface;

public interface RMIServerInterface extends Remote{
	public boolean registerClient(RMIClientInterface client) throws RemoteException;
	public boolean deregisterClient(RMIClientInterface client) throws RemoteException;
	public boolean shotThrowed(Throw shot) throws RemoteException;
}
