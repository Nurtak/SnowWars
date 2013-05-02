package ch.hsr.se2p.snowwars.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSession;

public class Lobby {

    private Map<User, LobbyServerSession> users = new HashMap<User, LobbyServerSession>();
    
    public synchronized boolean isNameAvalible(String name){
        for (User user : users.keySet()) {
            if (user.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
    
    public synchronized boolean addNewUser(User user, LobbyServerSession lobbySession) throws UsernameAlreadyTakenException{
        if (users.containsKey(user)) {
            throw new UsernameAlreadyTakenException();
        } else {
            users.put(user, lobbySession);
            return true;
        }
    }
    
    public synchronized Set<User> getUsers(){
        return users.keySet();
    }

    public synchronized void leave(User user) {
        users.remove(user);
    }

    public synchronized void inviteUser(User user, User selectedUser) {
        users.get(selectedUser);
        //TODO
    }

}
