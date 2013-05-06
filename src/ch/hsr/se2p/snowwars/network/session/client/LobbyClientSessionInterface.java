package ch.hsr.se2p.snowwars.network.session.client;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public interface LobbyClientSessionInterface {

    public void receiveInvitation(User from);

    public void receiveInvitationTimeout(User from);

    public GameClientSessionInterface startGame(GameServerSessionInterface gameServerSessionInterface);

}
