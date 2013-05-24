package ch.hsr.se2p.snowwars.network.client.session;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.server.session.GameServerSessionInterface;

public interface LobbyClientSessionInterface extends Remote {

	public void receiveLobbyUpdate(Set<User> users) throws RemoteException;

	public void receiveInvitation(User from) throws RemoteException;

	public void receiveInvitationAnswer(User from, InvitationAnswer answer) throws RemoteException;

	public void startGame(GameServerSessionInterface gameServerSessionInterface) throws RemoteException;

}
