package ch.hsr.se2p.snowwars.client.lobby.view;

import javax.swing.JPanel;

public interface ClientViewMainInterface{
	public void addPanel(JPanel jPanel, String name);
	public void leaveLobby();
	public void previousCard();
	public void nextCard();
	public void exit();
}