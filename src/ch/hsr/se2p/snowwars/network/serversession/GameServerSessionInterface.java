package ch.hsr.se2p.snowwars.network.serversession;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.clientsession.GameClientSessionInterface;

public interface GameServerSessionInterface extends Remote {

	public void startBuilding() throws RemoteException;

	public void shoot(Shot shot) throws RemoteException;

	public void setReady() throws RemoteException;

	public PlayerPosition getPlayerPosition() throws RemoteException;
	
	public Player getLeftPlayer() throws RemoteException;

	public Player getRightPlayer() throws RemoteException;

	public void setGameClientSessionInterface(GameClientSessionInterface gcsi) throws RemoteException;

	public void quitGame() throws RemoteException;
}
