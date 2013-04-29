package ch.hsr.se2p.snowwars.model.remoteinterfaces;

import java.rmi.Remote;
import java.util.List;

public interface LobbyInterface extends Remote {

    public EnterLobbyReturnValues enter(String username);

    public boolean leave();

    public List<UserInterface> getLoggedInUsers();

    public InviteUserReturnValues inviteUser(UserInterface selectedUser);

    public void startGame(UserInterface selectedUser);

}
