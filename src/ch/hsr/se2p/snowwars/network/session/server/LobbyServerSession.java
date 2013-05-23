package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.exceptions.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.session.client.LobbyClientSessionInterface;

public class LobbyServerSession extends UnicastRemoteObject implements LobbyServerSessionInterface, LobbyClientSessionInterface {

	private static final long serialVersionUID = -3804423975783216087L;

	private final static Logger logger = Logger.getLogger(LobbyServerSession.class.getPackage().getName());

	private User user;
	private Lobby lobby;
	private LobbyClientSessionInterface lobbyClientSessionInterface;

	public LobbyServerSession(User user, Lobby lobby, LobbyClientSessionInterface lobbyClientSessionInterface) throws RemoteException, UsernameAlreadyTakenException {
		this.user = user;
		this.lobby = lobby;
		this.lobbyClientSessionInterface = lobbyClientSessionInterface;
		lobby.addSession(this);
	}

	@Override
	public ConnectedServerSessionInterface leaveLobby() throws SnowWarsRMIException {
		lobby.leave(this);
		ConnectedServerSession connectedSession;
		try {
			connectedSession = new ConnectedServerSession(lobby);
		} catch (RemoteException e) {
			throw new SnowWarsRMIException(e.getMessage());
		}
		return connectedSession;
	}

	@Override
	public Set<User> getUsers() {
		return lobby.getUsers();
	}

	@Override
	public void inviteUser(User selectedUser) throws UserIsNotInLobbyException {
		lobby.inviteUser(this, selectedUser);
	}

	@Override
	public void answerInvitation(User invitingUser, InvitationAnswer answer) throws RemoteException, UserIsNotInLobbyException {
		lobby.answerInvitation(this, invitingUser, answer);
	}

	public User getUser() {
		return user;
	}

	public void startGame(final GameServerSessionInterface gcsi) {
		// starts in new thread, because server don't wants to wait for
		// user-answer
		new Thread() {
			public void run() {
				try {
					lobbyClientSessionInterface.startGame(gcsi);
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	public void receiveLobbyUpdate(Set<User> users) throws RemoteException {
		lobbyClientSessionInterface.receiveLobbyUpdate(users);
	}

	public void receiveInvitation(final User from) throws RemoteException {
		// starts in new thread, because server don't wants to wait for
		// user-answer
		new Thread() {
			public void run() {
				try {
					lobbyClientSessionInterface.receiveInvitation(from);
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	public void receiveInvitationAnswer(final User from, final InvitationAnswer answer) throws RemoteException {
		// starts in new thread, because server don't wants to wait for
		// user-answer
		new Thread() {
			public void run() {
				try {
					lobbyClientSessionInterface.receiveInvitationAnswer(from, answer);
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

    @Override
    public int hashCode() {
        final int prime = 31;
        return prime + ((user == null) ? 0 : user.hashCode());
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (getClass() != obj.getClass())
            return false;
        LobbyServerSession other = (LobbyServerSession) obj;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
	
}
