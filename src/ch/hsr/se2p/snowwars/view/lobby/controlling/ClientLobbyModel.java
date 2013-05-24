package ch.hsr.se2p.snowwars.view.lobby.controlling;

import java.io.Serializable;
import java.util.Observable;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.User;

public class ClientLobbyModel extends Observable implements Serializable {

	private static final long serialVersionUID = -7674483417217484822L;

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
