package ch.hsr.se2p.snowwars.view.lobby.controlling;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.exceptions.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.Invitation.InvitationAnswer;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.session.client.LobbyClientSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSessionInterface;
import ch.hsr.se2p.snowwars.view.lobby.presentation.ClientViewMain;

public class ClientLobbyController extends UnicastRemoteObject implements LobbyClientSessionInterface, Serializable {

	private static final long serialVersionUID = -7299346634181477899L;
	private final static Logger logger = Logger.getLogger(ClientLobbyController.class.getPackage().getName());

	private SnowWarsClientInterface snowWarsClientInterface;
	private ConnectedServerSessionInterface connectedServerSessionInterface;
	private ClientLobbyModel clientLobbyModel;
	private ClientViewMain clientViewMain;
	private LobbyServerSessionInterface lobbyServerSessionInterface;

	public ClientLobbyController(SnowWarsClientInterface snowWarsClientInterface, ConnectedServerSessionInterface connectedServerSessionInterface) throws RemoteException {
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
			lobbyServerSessionInterface = connectedServerSessionInterface.registerAtLobby(this, user);
			clientLobbyModel.setUser(user);
			clientLobbyModel.setUsers(lobbyServerSessionInterface.getUsers());
		} catch (RemoteException | SnowWarsRMIException | UsernameAlreadyTakenException e) {
			logger.error(e.getMessage(), e);
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
		int resultValue = JOptionPane.showConfirmDialog(clientViewMain, from.getName() + " wants to play a game!", "Invitation", JOptionPane.YES_NO_OPTION);
		
		// answer invitation
		InvitationAnswer answer = InvitationAnswer.TIMEOUT;
		switch (resultValue) {
			case 0 :
				answer = InvitationAnswer.ACCEPTED;
				break;
			case 1 :
				answer = InvitationAnswer.DISCARDED;
				break;
		}

		sendInvitationAnswer(from, answer);
	}

	private void sendInvitationAnswer(final User from, final InvitationAnswer answer) {
		new Thread() {
			public void run() {
				logger.info("Sending Invitation-Answer...");
				try {
					lobbyServerSessionInterface.answerInvitation(from, answer);
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				} catch (UserIsNotInLobbyException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	@Override
	public void receiveInvitationAnswer(User from, InvitationAnswer answer) {
		switch (answer) {
			case ACCEPTED :
				break;
			case DISCARDED :
				JOptionPane.showMessageDialog(clientViewMain, "User " + from + " discarded your invitation!", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			case USER_ALREADY_INVITED :
				JOptionPane.showMessageDialog(clientViewMain, "User " + from + " cannot be invited at the moment. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
				break;
			default :
				JOptionPane.showMessageDialog(clientViewMain, "User " + from + " didn't answer your invitation! Timeout has occurred.", "Error", JOptionPane.ERROR_MESSAGE);
				break;
		}
	}

	@Override
	public void startGame(final GameServerSessionInterface gameServerSessionInterface) {
		logger.info("startGame received!");
		
		new Thread(){
			public void run(){
				try {
					lobbyServerSessionInterface.leaveLobby();
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				} catch (SnowWarsRMIException e) {
					logger.error(e.getMessage(), e);
				}
				clientViewMain.setVisible(false);
				
				snowWarsClientInterface.enterGame(gameServerSessionInterface);
			}
		}.start();
	}

	public void inviteUser(final User selectedUser) throws RemoteException, UserIsNotInLobbyException {
		new Thread() {
			public void run() {
				logger.info("Sending Invitation to " + selectedUser.getName());
				try {
					lobbyServerSessionInterface.inviteUser(selectedUser);
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				} catch (UserIsNotInLobbyException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();

	}

	public void leaveLobby() throws RemoteException, SnowWarsRMIException {
		lobbyServerSessionInterface.leaveLobby();
	}
}
