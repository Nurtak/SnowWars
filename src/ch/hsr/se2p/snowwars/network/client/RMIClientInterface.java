package ch.hsr.se2p.snowwars.network.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote{
	public void backCall() throws RemoteException;
}
