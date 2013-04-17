package ch.hsr.se2p.snowwars.view.game;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import ch.hsr.se2p.snowwars.model.Shot;

public class ViewGame extends JFrame implements Observer {
	private static final long serialVersionUID = -7803629994015778818L;

	protected final static int GAME_WIDTH = 1000;
	protected final static int GAME_HEIGHT = 600;
	private final static String GAME_TITLE = "Snow Wars";

	private Board board;

	private final ViewGameController viewGameController;

	public ViewGame(ViewGameController vgc) {
		this.viewGameController = vgc;
		vgc.addObserver(this);

		initializeGui();
	}

	private void initializeGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setLocationRelativeTo(null);
		setTitle(GAME_TITLE);
		setResizable(false);
		setVisible(true);

		board = new Board(this);
		add(board);
	}

	public void newShotRequest(Shot shot) {
		viewGameController.sendShotRequest(shot);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		String errorMessage = viewGameController.getErrorMessage();
		if (!errorMessage.equals("")) {
			JOptionPane.showMessageDialog(new JPanel(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
		}

		Shot newShot = viewGameController.getNextShot();
		if(newShot != null){
			Snowball sn = new Snowball(newShot.getAngle(), newShot.getStength());
			board.fire(sn);
		}
	}
}