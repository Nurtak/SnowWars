package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSession;

public class Lobby {

    private Set<LobbyServerSession> users = new HashSet<LobbyServerSession>();

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
            for (LobbyServerSession userSession : users) {
                try {
                    if (!userSession.equals(lobbyServerSession)) {                        
                        userSession.getLobbyClientSessionInterface().receiveLobbyUpdate(getUsers());
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

    public synchronized void inviteUser(LobbyServerSession lobbyServerSession, User target) {
        User from = lobbyServerSession.getUser();
        for (LobbyServerSession userSession : users) {
            if (userSession.getUser().equals(target)) {
                try {
                    userSession.getLobbyClientSessionInterface().receiveInvitation(from);
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // just one
                return;
            }
        }
    }

    public synchronized void leave(LobbyServerSession lobbyServerSession) {
        users.remove(lobbyServerSession);
    }

}
