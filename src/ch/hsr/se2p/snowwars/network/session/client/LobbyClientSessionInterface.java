package ch.hsr.se2p.snowwars.network.session.client;

import ch.hsr.se2p.snowwars.model.User;

public interface LobbyClientSessionInterface {

    public void receiveInvitation(User from);
    
    public void receiveInvitationTimeout(User from);
    
    public void startGame();
}
