package ch.hsr.se2p.snowwars.controller.lobby;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;
import ch.hsr.se2p.snowwars.network.session.client.LobbyClientSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSessionInterface;
import ch.hsr.se2p.snowwars.view.lobby.ClientViewMain;

public class ClientLobbyController extends UnicastRemoteObject implements
		LobbyClientSessionInterface, Serializable {

	private static final long serialVersionUID = -7299346634181477899L;
	private final static Logger logger = Logger
			.getLogger(ClientLobbyController.class.getPackage().getName());

	private SnowWarsClientInterface snowWarsClientInterface;
	private ConnectedServerSessionInterface connectedServerSessionInterface;
	private ClientLobbyModel clientLobbyModel;
	private ClientViewMain clientViewMain;
	private LobbyServerSessionInterface lobbyServerSessionInterface;

	public ClientLobbyController(
			SnowWarsClientInterface snowWarsClientInterface,
			ConnectedServerSessionInterface connectedServerSessionInterface)
			throws RemoteException {
		this.snowWarsClientInterface = snowWarsClientInterface;
		this.connectedServerSessionInterface = connectedServerSessionInterface;
		clientLobbyModel = new ClientLobbyModel();
		clientViewMain = new ClientViewMain(clientLobbyModel, this);
		clientLobbyModel.addObserver(clientViewMain);
	}

	public boolean isNameAvailable(String name) throws RemoteException {
		return connectedServerSessionInterface.isNameAvailable(name);
	}

	public void registerAtLobby(User user) {
		try {
			lobbyServerSessionInterface = connectedServerSessionInterface
					.registerAtLobby(this, user);
			clientLobbyModel.setUser(user);
			clientLobbyModel.setUsers(lobbyServerSessionInterface.getUsers());
		} catch (RemoteException | SnowWarsRMIException
				| UsernameAlreadyTakenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void receiveLobbyUpdate(Set<User> users) throws RemoteException {
		logger.info("new users received");
		clientLobbyModel.setUsers(users);
	}

	@Override
	public void receiveInvitation(User from) {
		logger.info("Invitation received from " + from.getName());
		int resultValue = JOptionPane.showConfirmDialog(clientViewMain,
				from.getName() + " wants to play a game!");

		// answer invitation
		InvitationAnswer answer = InvitationAnswer.TIMEOUT;
		switch (resultValue) {
		case 0:
			answer = InvitationAnswer.ACCEPTED;
			break;
		case 1:
			answer = InvitationAnswer.DISCARDED;
			break;
		}

		sendInvitationAnswer(from, answer);
	}
	
	private void sendInvitationAnswer(final User from, final InvitationAnswer answer){
		new Thread(){
			public void run(){
				logger.info("Sending Invitation-Answer...");
				try {
					lobbyServerSessionInterface.answerInvitation(from, answer);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (UserIsNotInLobbyException e) {
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	@Override
	public void receiveInvitationAnswer(User from, InvitationAnswer answer) {
		switch(answer){
		case ACCEPTED:
			JOptionPane.showMessageDialog(clientViewMain, "User " + from + " accepted your invitation!");
			break;
		case DISCARDED:
			JOptionPane.showMessageDialog(clientViewMain, "User " + from + " discarded your invitation!", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		case USER_ALREADY_INVITED: 
			JOptionPane.showMessageDialog(clientViewMain, "User " + from + " was already invited!", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		default:
			JOptionPane.showMessageDialog(clientViewMain, "User " + from + " didn't answer your invitation! Timeout has occurred.", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}

	@Override
	public void startGame(
			GameServerSessionInterface gameServerSessionInterface) {
		logger.info("startGame received!");

		GameClientSessionInterface gameClientSession = null;
		try {
			gameClientSession = new ViewGameController(snowWarsClientInterface, new GameClient(gameServerSessionInterface));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try {
			gameServerSessionInterface.setGameClientSessionInterface(gameClientSession);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		snowWarsClientInterface.enterGame((ViewGameController) gameClientSession);
	}

	public void inviteUser(final User selectedUser) throws RemoteException,
			UserIsNotInLobbyException {
		new Thread() {
			public void run() {
				logger.info("Sending Invitation to " + selectedUser.getName());
				try {
					lobbyServerSessionInterface.inviteUser(selectedUser);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (UserIsNotInLobbyException e) {
					e.printStackTrace();
				}
			}
		}.start();

	}

	public void leaveLobby() throws RemoteException, SnowWarsRMIException {
		// TODO Auto-generated method stub
	}
}
