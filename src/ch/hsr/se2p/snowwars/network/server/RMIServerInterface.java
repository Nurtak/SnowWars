package ch.hsr.se2p.snowwars.network.server;

import java.rmi.RemoteException;

public interface RMIServerInterface {
	public boolean joinGame() throws RemoteException;
}
