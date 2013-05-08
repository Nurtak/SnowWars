package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSession;

public class Invitation {
	private LobbyServerSession invitingUserSession;
	private LobbyServerSession answeringUserSession;

	public static enum InvitationAnswer {
		ACCEPTED, DISCARDED, TIMEOUT, USER_ALREADY_INVITED
	}

	public Invitation(LobbyServerSession invitingUser,
			LobbyServerSession answeringUser) throws RemoteException {
		this.invitingUserSession = invitingUser;
		this.answeringUserSession = answeringUser;
	}

	public LobbyServerSession getInvitingLobbyServerSession() {
		return this.invitingUserSession;
	}

	public LobbyServerSession getAnsweringLobbyServerSession() {
		return this.answeringUserSession;
	}

	public void answerInvitation(InvitationAnswer answer) {
		try {
			invitingUserSession
					.getLobbyClientSessionInterface()
					.receiveInvitationAnswer(answeringUserSession.getUser(), answer);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void sendInvitation() {
		try {
			this.answeringUserSession.getLobbyClientSessionInterface()
					.receiveInvitation(invitingUserSession.getUser());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((answeringUserSession == null) ? 0 : answeringUserSession
						.getUser().hashCode());
		result = prime
				* result
				+ ((invitingUserSession == null) ? 0 : invitingUserSession
						.getUser().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Invitation other = (Invitation) obj;
		if (answeringUserSession == null) {
			if (other.answeringUserSession != null)
				return false;
		} else if (!answeringUserSession.getUser().equals(
				other.answeringUserSession.getUser()))
			return false;
		if (invitingUserSession == null) {
			if (other.invitingUserSession != null)
				return false;
		} else if (!invitingUserSession.getUser().equals(
				other.invitingUserSession.getUser()))
			return false;
		return true;
	}
}
