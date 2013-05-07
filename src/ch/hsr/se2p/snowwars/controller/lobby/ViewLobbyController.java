package ch.hsr.se2p.snowwars.controller.lobby;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSession;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;
import ch.hsr.se2p.snowwars.network.session.client.LobbyClientSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSessionInterface;
import ch.hsr.se2p.snowwars.view.lobby.ViewMain;

public class ViewLobbyController extends UnicastRemoteObject implements LobbyClientSessionInterface, Serializable {

    private static final long serialVersionUID = -7299346634181477899L;
    private final static Logger logger = Logger.getLogger(ViewLobbyController.class.getPackage().getName());
    
    private SnowWarsClientInterface snowWarsClientInterface;
    private ConnectedServerSessionInterface connectedServerSessionInterface;
    private ViewLobbyModel viewLobbyModel;
    private ViewMain viewMain;
    private LobbyServerSessionInterface lobbyServerSessionInterface;

    public ViewLobbyController(SnowWarsClientInterface snowWarsClientInterface, ConnectedServerSessionInterface connectedServerSessionInterface)
            throws RemoteException {
        this.snowWarsClientInterface = snowWarsClientInterface;
        this.connectedServerSessionInterface = connectedServerSessionInterface;
        viewLobbyModel = new ViewLobbyModel();
        viewMain = new ViewMain(viewLobbyModel, this);
        viewLobbyModel.addObserver(viewMain);
    }

    public boolean isNameAvailable(String name) throws RemoteException {
        return connectedServerSessionInterface.isNameAvailable(name);
    }

    public void registerAtLobby(User user) {
        try {
            lobbyServerSessionInterface = connectedServerSessionInterface.registerAtLobby(this, user);
            viewLobbyModel.setUser(user);
            viewLobbyModel.setUsers(lobbyServerSessionInterface.getUsers());
        } catch (RemoteException | SnowWarsRMIException | UsernameAlreadyTakenException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void receiveInvitation(User from) {
        logger.info("Invitation received from " + from.getName());
        // TODO display invitation
    }

    @Override
    public void receiveInvitationTimeout(User from) {
        logger.info("Invitation from " + from.getName() + " timed out");
        // TODO display invitation has been canceled
    }

    @Override
    public GameClientSessionInterface startGame(GameServerSessionInterface gameServerSessionInterface) {
        logger.info("startGame received!");

        GameClientSession gameClientSession = null;
        try {
            gameClientSession = new GameClientSession();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        snowWarsClientInterface.enterGame(snowWarsClientInterface, gameServerSessionInterface);
        return gameClientSession;
    }
    
    public void inviteUser(User selectedUser) throws RemoteException, UserIsNotInLobbyException {
        logger.info("Sending Invitation to " + selectedUser.getName());
        lobbyServerSessionInterface.inviteUser(selectedUser);        
    }
    
    public void leaveLobby() throws RemoteException, SnowWarsRMIException {
        // TODO Auto-generated method stub
    }

}
