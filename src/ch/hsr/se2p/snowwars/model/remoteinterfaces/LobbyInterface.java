package ch.hsr.se2p.snowwars.model.remoteinterfaces;

import java.rmi.Remote;
import java.util.List;

public interface LobbyInterface extends Remote {

    public enum enterReturn {
        OK, USERNAME_ALREADY_IN_USE, USERNAME_TOO_LONG;
    }

    public enum inviteUserReturn {
        OK, SELECTED_USER_DECLINES_INVITATION, SELECTED_USER_TIMEED_OUT;
    }

    public enterReturn enter(String username);

    public boolean leave();

    public List<UserInterface> getLoggedInUsers();

    public inviteUserReturn inviteUser(UserInterface selectedUser);

    public void startGame(UserInterface selectedUser);

}
