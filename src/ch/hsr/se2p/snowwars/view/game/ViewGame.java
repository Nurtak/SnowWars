package ch.hsr.se2p.snowwars.view.game;

import javax.swing.JFrame;

public class ViewGame extends JFrame {
	private static final long serialVersionUID = -7803629994015778818L;
	
	protected final static int GAME_WIDTH = 1000;
	protected final static int GAME_HEIGHT = 600;
	private final static String GAME_TITLE = "Snow Wars";
	
	public ViewGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setLocationRelativeTo(null);
		setTitle(GAME_TITLE);
		setResizable(false);
		
		add(new Board());
	}
	
	public void showGui(){
		setVisible(true);
	}
}