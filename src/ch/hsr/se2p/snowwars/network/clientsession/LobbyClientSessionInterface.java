package ch.hsr.se2p.snowwars.network.clientsession;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.serversession.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.util.InvitationAnswer;

public interface LobbyClientSessionInterface extends Remote {

	public void receiveLobbyUpdate(Set<User> users) throws RemoteException;

	public void receiveInvitation(User from) throws RemoteException;

	public void receiveInvitationAnswer(User from, InvitationAnswer answer) throws RemoteException;

	public void startGame(GameServerSessionInterface gameServerSessionInterface) throws RemoteException;

}
