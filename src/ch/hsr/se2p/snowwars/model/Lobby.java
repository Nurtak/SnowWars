package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSession;

public class Lobby {

	private Set<LobbyServerSession> users = new HashSet<LobbyServerSession>();
	private Set<Invitation> invitationList = new HashSet<Invitation>();
	
	public synchronized boolean isNameAvailable(String name) {
		for (LobbyServerSession lobbyServerSession : users) {
			if (lobbyServerSession.getUser().getName().equals(name)) {
				return false;
			}
		}
		return true;
	}

	public synchronized boolean addSession(LobbyServerSession lobbyServerSession)
			throws UsernameAlreadyTakenException {
		if (users.add(lobbyServerSession)) {
			for (LobbyServerSession userSession : users) {
				try {
					if (!userSession.equals(lobbyServerSession)) {
						userSession.getLobbyClientSessionInterface()
								.receiveLobbyUpdate(getUsers());
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
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

	public synchronized void inviteUser(LobbyServerSession lobbyServerSession,
			User target) {
		// get lobbyserver session of target
		for (LobbyServerSession userSession : users) {
			if (userSession.getUser().equals(target)) {
				// create new invitation
				Invitation invitation;
				try {
					invitation = new Invitation(lobbyServerSession,
							userSession);

					// if invitation already existing
					if (invitationList.contains(invitation)) {
						invitation
								.answerInvitation(InvitationAnswer.USER_ALREADY_INVITED);
					} else {
						invitation.sendInvitation();
					}
				} catch (RemoteException e) {}
			}
		}
	}

	public synchronized void answerInvitation(
			LobbyServerSession answeringUserSession, User invitingUser,
			InvitationAnswer answer) {
		for (LobbyServerSession userSession : users) {
			if (userSession.getUser().equals(invitingUser)) {
				// check if there is an invitation
				Invitation invitation;
				try {
					invitation = new Invitation(userSession,
							answeringUserSession);
					if (invitationList.contains(invitation)) {
						invitation.answerInvitation(answer);
					}
					invitationList.remove(invitation);
				} catch (RemoteException e) {}
			}
		}
	}

	public synchronized void leave(LobbyServerSession lobbyServerSession) {
		users.remove(lobbyServerSession);
	}
}
