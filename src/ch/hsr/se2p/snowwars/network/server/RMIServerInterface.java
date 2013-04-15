package ch.hsr.se2p.snowwars.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.network.client.RMIClientInterface;

public interface RMIServerInterface extends Remote{
	public boolean joinGame(RMIClientInterface client) throws RemoteException;
}
