package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSession;

public class Invitation {
	private final static Logger logger = Logger.getLogger(Invitation.class.getPackage().getName());

	private LobbyServerSession invitingUserSession;
	private LobbyServerSession answeringUserSession;

	public static enum InvitationAnswer {
		ACCEPTED, DISCARDED, TIMEOUT, USER_ALREADY_INVITED
	}

	public Invitation(LobbyServerSession invitingUser, LobbyServerSession answeringUser) throws RemoteException {
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
		logger.info("Sending invitation-answer from " + answeringUserSession.getUser() + " to " + invitingUserSession.getUser());
		try {
			invitingUserSession.receiveInvitationAnswer(answeringUserSession.getUser(), answer);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void sendInvitation() {
		logger.info("Sending invitation from " + invitingUserSession.getUser() + " to " + answeringUserSession.getUser());
		try {
			this.answeringUserSession.receiveInvitation(invitingUserSession.getUser());
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answeringUserSession == null) ? 0 : answeringUserSession.getUser().hashCode());
		result = prime * result + ((invitingUserSession == null) ? 0 : invitingUserSession.getUser().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Invitation otherInvitation = (Invitation) obj;
		User invitingUser = invitingUserSession.getUser();
		User answeringUser = answeringUserSession.getUser();
		User otherInvitingUser = otherInvitation.invitingUserSession.getUser();
		User otherAnsweringUser = otherInvitation.answeringUserSession.getUser();

		if (invitingUser.equals(otherInvitingUser) || invitingUser.equals(otherAnsweringUser)) {
			return true;
		} else if (answeringUser.equals(otherInvitingUser) || answeringUser.equals(otherAnsweringUser)) {
			return true;
		}
		return false;
	}
}
