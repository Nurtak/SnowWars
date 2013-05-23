package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.RemoteException;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.network.session.client.LobbyClientSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSession;

public class LobbyTest {

    private Lobby lobby;

    private class LobbyClientSessionFake implements LobbyClientSessionInterface {

        @Override
        public void receiveLobbyUpdate(Set<User> users) throws RemoteException {
        }

        @Override
        public void receiveInvitation(User from) throws RemoteException {
        }

        @Override
        public void receiveInvitationAnswer(User from, InvitationAnswer answer) throws RemoteException {
        }

        @Override
        public void startGame(GameServerSessionInterface gameServerSessionInterface) throws RemoteException {
        }

    }

    @Before
    public void setUp() throws Exception {
        lobby = new Lobby();
    }

    @Test
    public void testIsNameAvailableTrue() {
        String name = "Donald Duck";
        assertTrue(lobby.isNameAvailable(name));
    }

    @Test
    public void testIsNameAvailableFalse() {
        String name = "Donald Duck";
        User user = new User(name);
        LobbyClientSessionFake lobbyClientSessionFake = new LobbyClientSessionFake();
        try {
            new LobbyServerSession(user, lobby, lobbyClientSessionFake);
        } catch (RemoteException | UsernameAlreadyTakenException e) {
            fail();
        }
        assertFalse(lobby.isNameAvailable(name));
    }

    @Test
    public void testAddSessionTrue() {
        String name = "Donald Duck";
        User user = new User(name);
        LobbyClientSessionFake lobbyClientSessionFake = new LobbyClientSessionFake();
        try {
            new LobbyServerSession(user, lobby, lobbyClientSessionFake);
        } catch (RemoteException | UsernameAlreadyTakenException e) {
            fail();
        }
    }

    @Test
    public void testAddSessionFalse() {
        String name = "Donald Duck";
        User user1 = new User(name);
        User user2 = new User(name);
        LobbyClientSessionFake lobbyClientSessionFake = new LobbyClientSessionFake();
        try {
            LobbyServerSession lobbyServerSession1 = new LobbyServerSession(user1, lobby, lobbyClientSessionFake);
            LobbyServerSession lobbyServerSession2 = new LobbyServerSession(user2, lobby, lobbyClientSessionFake);
            
            System.out.println(user1.equals(user2));
            System.out.println(lobbyServerSession1.equals(lobbyServerSession2));
            
            System.out.println(user1.hashCode() + " == " +  user2.hashCode());
            System.out.println(lobbyServerSession1.hashCode() + " == " +  lobbyServerSession2.hashCode());
            
            
            fail();
        } catch (RemoteException e) {
            fail();
        } catch (UsernameAlreadyTakenException e) {
            // Okay
        }
    }

    @Test
    public void testGetUsersEmpty() {
        assertTrue(lobby.getUsers().isEmpty());
    }

    @Test
    public void testGetUsersWithUser() {
        String name = "Donald Duck";
        User user = new User(name);
        LobbyClientSessionFake lobbyClientSessionFake = new LobbyClientSessionFake();
        try {
            new LobbyServerSession(user, lobby, lobbyClientSessionFake);
        } catch (RemoteException | UsernameAlreadyTakenException e) {
            fail();
        }
        assertTrue(lobby.getUsers().size() == 1);
        assertTrue(lobby.getUsers().contains(user));
    }

}
