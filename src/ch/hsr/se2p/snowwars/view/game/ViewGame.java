package ch.hsr.se2p.snowwars.view.game;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

public class ViewGame extends JFrame implements Observer{
	private static final long serialVersionUID = -7803629994015778818L;
	
	protected final static int GAME_WIDTH = 1000;
	protected final static int GAME_HEIGHT = 600;
	private final static String GAME_TITLE = "Snow Wars";
	
	private final ViewGameController viewGameController;
	
	public ViewGame(ViewGameController vgc) {
		this.viewGameController = vgc;
		vgc.addObserver(this);
		
		initializeGui();
	}
	
	private void initializeGui(){
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

	@Override
	public void update(Observable arg0, Object arg1) {
		
	}
}