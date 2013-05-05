package ch.hsr.se2p.snowwars.network.session.client;

import ch.hsr.se2p.snowwars.model.User;

public class LobbyClientSession implements LobbyClientSessionInterface {

    private User user;

    public LobbyClientSession(User user) {
        this.user = user;
    }

    @Override
    public void receiveInvitation(User from) {
        // TODO Auto-generated method stub

    }

    @Override
    public void receiveInvitationTimeout(User from) {
        // TODO Auto-generated method stub

    }

    @Override
    public void startGame() {
        // TODO Auto-generated method stub

    }

}
