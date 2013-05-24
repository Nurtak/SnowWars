package ch.hsr.se2p.snowwars.network.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.network.server.session.ConnectedServerSessionInterface;

public interface RMIServerInterface extends Remote {

	public ConnectedServerSessionInterface connect() throws RemoteException;

}
