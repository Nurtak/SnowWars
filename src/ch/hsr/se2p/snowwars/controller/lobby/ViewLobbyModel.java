package ch.hsr.se2p.snowwars.controller.lobby;

import java.util.Observable;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.User;

public class ViewLobbyModel extends Observable {

    private User user;
    private Set<User> users;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        changed();
    }
    
    public Set<User> getUsers() {
        return users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
        changed();
    }
    
    public void changed() {
        setChanged();
        notifyObservers();
    }
}
