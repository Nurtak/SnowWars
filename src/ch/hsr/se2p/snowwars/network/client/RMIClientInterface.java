package ch.hsr.se2p.snowwars.network.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Shot;

public interface RMIClientInterface extends Remote {
    public void shotThrowed(Shot shot) throws RemoteException;

    // public boolean receiveInvitation() throws RemoteException;
}
