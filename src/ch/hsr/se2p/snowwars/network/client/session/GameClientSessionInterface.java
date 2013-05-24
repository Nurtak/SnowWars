package ch.hsr.se2p.snowwars.network.client.session;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Shot;

public interface GameClientSessionInterface extends Remote {

	public void receiveShot(Shot shot) throws RemoteException;

	public void playerIsBuilding(PlayerPosition playerPosition) throws RemoteException;

	public void updatePlayerHitPoints(PlayerPosition playerPosition, int hitPoints) throws RemoteException;

	public void youWon() throws RemoteException;

	public void youLost() throws RemoteException;

	public void opponentQuitGame() throws RemoteException;

	public void setCountdownTime(int time) throws RemoteException;

	public void countdownEnded() throws RemoteException;
}
