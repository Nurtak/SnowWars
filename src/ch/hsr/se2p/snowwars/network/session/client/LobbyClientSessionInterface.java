package ch.hsr.se2p.snowwars.network.session.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public interface LobbyClientSessionInterface extends Remote {

    public void receiveInvitation(User from) throws RemoteException;

    public void receiveInvitationTimeout(User from) throws RemoteException;

    public GameClientSessionInterface startGame(GameServerSessionInterface gameServerSessionInterface) throws RemoteException;

}
