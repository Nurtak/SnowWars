package ch.hsr.se2p.snowwars.model.server;

import java.util.List;

import ch.hsr.se2p.snowwars.model.client.User;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.LobbyInterface;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.UserInterface;

public class Lobby implements LobbyInterface{

	@Override
	public enterReturn enter(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean leave() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<User> getLoggedInUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public inviteUserReturn inviteUser(UserInterface selectedUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void startGame(UserInterface selectedUser) {
		// TODO Auto-generated method stub
		
	}

}
