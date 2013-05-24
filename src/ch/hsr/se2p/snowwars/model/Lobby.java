package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.network.server.session.GameServerSession;
import ch.hsr.se2p.snowwars.network.server.session.LobbyServerSession;

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

    public synchronized void inviteUser(LobbyServerSession lobbyServerSession, User targetUser) {
        LobbyServerSession targetLobbyServerSession = getLobbyServerSessionOfUser(targetUser);
        Invitation invitation;
        try {
            invitation = new Invitation(lobbyServerSession, targetLobbyServerSession);
            // if invitation already existing
            if (invitationList.contains(invitation)) {
                invitation.answerInvitation(InvitationAnswer.USER_ALREADY_INVITED);
            } else {
                invitationList.add(invitation);
                invitation.sendInvitation();
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

    }

    private synchronized LobbyServerSession getLobbyServerSessionOfUser(User user) {
        for (LobbyServerSession lobbyServerSession : users) {
            if (lobbyServerSession.getUser().equals(user)) {
                return lobbyServerSession;
            }
        }
        return null;
    }

    public synchronized void answerInvitation(LobbyServerSession answeringUserSession, User invitingUser, InvitationAnswer answer) {
        LobbyServerSession invitingLobbyServerSession = getLobbyServerSessionOfUser(invitingUser);
        // check if there is an invitation
        try {
            Invitation invitation = new Invitation(invitingLobbyServerSession, answeringUserSession);
            if (invitationList.contains(invitation)) {
                invitation.answerInvitation(answer);

                if (answer == InvitationAnswer.ACCEPTED) {
                    startNewGame(invitingLobbyServerSession, answeringUserSession);
                }
            }
            invitationList.remove(invitation);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
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
            logger.error(e.getMessage(), e);
        }
    }

    public synchronized void leave(LobbyServerSession lobbyServerSession) {
        users.remove(lobbyServerSession);
        notifyUsersForLobbyUpdate(lobbyServerSession);
    }

    private void notifyUsersForLobbyUpdate(LobbyServerSession sessionNotToNotify) {
        for (LobbyServerSession userSession : users) {
            try {
                if (!userSession.equals(sessionNotToNotify)) {
                    userSession.receiveLobbyUpdate(getUsers());
                }
            } catch (RemoteException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
