package ch.hsr.se2p.snowwars.model.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.hsr.se2p.snowwars.model.client.Player;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.EnterLobbyReturnValues;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.InviteUserReturnValues;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.LobbyInterface;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.UserInterface;

public class Lobby implements LobbyInterface{

    private Map<String, Player> playerList = new HashMap<String, Player>();

	public EnterLobbyReturnValues enter(String username) {
		if (playerList.containsKey(username)) {
            return EnterLobbyReturnValues.USERNAME_ALREADY_IN_USE;
        }
		else {
            return EnterLobbyReturnValues.OK;
        }
	}

	public boolean leave() {
		//playerList
	    return false;
	}

	public List<UserInterface> getLoggedInUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	public InviteUserReturnValues inviteUser(UserInterface selectedUser) {
		// TODO Auto-generated method stub
		return null;
	}

	public void startGame(UserInterface selectedUser) {
		// TODO Auto-generated method stub
		
	}

}
