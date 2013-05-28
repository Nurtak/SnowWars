package ch.hsr.se2p.snowwars.viewcontrolling.lobby;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.rmi.RemoteException;
import java.util.Set;

import org.junit.Test;

import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.exceptions.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.client.session.LobbyClientSessionInterface;
import ch.hsr.se2p.snowwars.network.server.session.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.server.session.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.network.server.session.LobbyServerSessionInterface;
import ch.hsr.se2p.snowwars.view.lobby.controlling.ClientLobbyController;

public class ClientLobbyControllerTest {

    private String name = "Donald Duck";
    private User testUser = new User(name);

    private class ConnectedServerSessionFake implements ConnectedServerSessionInterface {

        @Override
        public boolean isNameAvailable(String name) throws RemoteException {
            return false;
        }

        @Override
        public LobbyServerSessionInterface registerAtLobby(LobbyClientSessionInterface lobbyClientSessionInterface, User user) throws RemoteException,
                SnowWarsRMIException, UsernameAlreadyTakenException {
            assertTrue(lobbyClientSessionInterface instanceof ClientLobbyController);
            assertEquals(testUser, user);
            return new LobbyServerSessionInterface() {

                @Override
                public ConnectedServerSessionInterface leaveLobby() throws RemoteException, SnowWarsRMIException {
                    return null;
                }

                @Override
                public void inviteUser(User selectedUser) throws RemoteException, UserIsNotInLobbyException {
                }

                @Override
                public Set<User> getUsers() throws RemoteException {
                    return null;
                }

                @Override
                public User getUser() throws RemoteException {
                    return null;
                }

                @Override
                public void answerInvitation(User invitingUser, InvitationAnswer answer) throws RemoteException, UserIsNotInLobbyException {
                }
            };
        }

    }

    private class SnowWarsClientFake implements SnowWarsClientInterface {

        @Override
        public void enterLobby(ConnectedServerSessionInterface connectedServerSessionInterface) {
        }

        @Override
        public void enterGame(GameServerSessionInterface gameServerSessionInterface) {
        }

        @Override
        public void startProgram() {
        }

    }

    @Test
    public void testRegisterAtLobby() throws RemoteException {
        ClientLobbyController clc = new ClientLobbyController(new SnowWarsClientFake(), new ConnectedServerSessionFake());
        clc.registerAtLobby(name);
    }
}
