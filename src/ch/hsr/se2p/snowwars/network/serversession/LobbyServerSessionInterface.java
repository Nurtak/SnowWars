package ch.hsr.se2p.snowwars.network.serversession;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.util.InvitationAnswer;
import ch.hsr.se2p.snowwars.util.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.util.exception.UserIsNotInLobbyException;

public interface LobbyServerSessionInterface extends Remote {

	public Set<User> getUsers() throws RemoteException;

	public void inviteUser(User selectedUser) throws RemoteException, UserIsNotInLobbyException;

	public void answerInvitation(User invitingUser, InvitationAnswer answer) throws RemoteException, UserIsNotInLobbyException;

	public ConnectedServerSessionInterface leaveLobby() throws RemoteException, SnowWarsRMIException;

	public User getUser() throws RemoteException;

}
