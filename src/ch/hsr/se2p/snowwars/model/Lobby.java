package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSession;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSession;

public class Lobby {
	private final static Logger logger = Logger.getLogger(Lobby.class.getPackage().getName());
	private Set<LobbyServerSession> users = new HashSet<LobbyServerSession>();
	private ArrayList<Invitation> invitationList = new ArrayList<Invitation>();

	public synchronized boolean isNameAvailable(String name) {
		for (LobbyServerSession lobbyServerSession : users) {
			if (lobbyServerSession.getUser().getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	public synchronized boolean addSession(LobbyServerSession lobbyServerSession) throws UsernameAlreadyTakenException {
		if (users.add(lobbyServerSession)) {
			notifyUsersForLobbyUpdate(lobbyServerSession);
			return true;
		} else {
			throw new UsernameAlreadyTakenException();
		}
	}

	public synchronized Set<User> getUsers() {
		Set<User> result = new HashSet<User>(users.size());
		for (LobbyServerSession lobbyServerSession : users) {
			result.add(lobbyServerSession.getUser());
		}
		return result;
	}

	public synchronized void inviteUser(LobbyServerSession lobbyServerSession, User target) {
		// get lobbyserver session of target
		for (LobbyServerSession userSession : users) {
			if (userSession.getUser().equals(target)) {
				// create new invitation
				Invitation invitation;
				try {
					invitation = new Invitation(lobbyServerSession, userSession);

					// if invitation already existing
					if (invitationList.contains(invitation)) {
						invitation.answerInvitation(InvitationAnswer.USER_ALREADY_INVITED);
					} else {
						invitationList.add(invitation);
						invitation.sendInvitation();
					}
				} catch (RemoteException e) {
				}
			}
		}
	}

	public synchronized void answerInvitation(LobbyServerSession answeringUserSession, User invitingUser, InvitationAnswer answer) {
		for (LobbyServerSession activeSession : users) {
			if (activeSession.getUser().equals(invitingUser)) {
				// check if there is an invitation
				try {
					Invitation invitation = new Invitation(activeSession, answeringUserSession);
					if (invitationList.contains(invitation)) {
						invitation.answerInvitation(answer);

						if (answer == InvitationAnswer.ACCEPTED) {
							startNewGame(activeSession, answeringUserSession);
						}
					}
					invitationList.remove(invitation);
				} catch (RemoteException e) {
				}
			}
		}
	}

	private void startNewGame(LobbyServerSession playerLeftLobbyServerSession, LobbyServerSession playerRightLobbyServerSession) {
		logger.info("Starting new game between " + playerLeftLobbyServerSession.getUser() + " and " + playerRightLobbyServerSession.getUser());

		try {
			GameServerSession playerLeftGameServerSession = new GameServerSession(playerLeftLobbyServerSession.getUser());
			GameServerSession playerRightGameServerSession = new GameServerSession(playerRightLobbyServerSession.getUser());
			GameServer gameServer = new GameServer(playerLeftGameServerSession, playerRightGameServerSession);
			playerLeftGameServerSession.setGameServer(gameServer);
			playerRightGameServerSession.setGameServer(gameServer);

			playerLeftLobbyServerSession.startGame(playerLeftGameServerSession);
			playerRightLobbyServerSession.startGame(playerRightGameServerSession);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public synchronized void leave(LobbyServerSession lobbyServerSession) {
		users.remove(lobbyServerSession);
		notifyUsersForLobbyUpdate(lobbyServerSession);
	}
	
	private void notifyUsersForLobbyUpdate(LobbyServerSession sessionNotToNotify){
		for (LobbyServerSession userSession : users) {
			try {
				if (!userSession.equals(sessionNotToNotify)) {
					userSession.receiveLobbyUpdate(getUsers());
				}
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
}
