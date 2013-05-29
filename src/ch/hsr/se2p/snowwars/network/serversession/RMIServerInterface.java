package ch.hsr.se2p.snowwars.network.serversession;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RMIServerInterface extends Remote {

	public ConnectedServerSessionInterface connect() throws RemoteException;

}
