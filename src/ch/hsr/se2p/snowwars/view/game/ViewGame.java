package ch.hsr.se2p.snowwars.view.game;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.model.Throw;

public class ViewGame extends JFrame implements Observer, WindowListener {
	private final static Logger logger = Logger.getLogger(ViewGame.class.getPackage().getName());
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
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setMinimumSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		setLocationRelativeTo(null);
		setTitle(GAME_TITLE);
		setResizable(false);
		setVisible(true);

		try {
			board = new Board(this);
		} catch (Exception e) {
			logger.error(e.getMessage());
			setVisible(false);
		}
		add(board);
		this.pack();
	}

	public void newShotRequest(Throw shot) {
		viewGameController.sendThrow(shot);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if (viewGameController.getShowNoConnectionError()) {
			Object[] options = { "Cancel", "Retry" };
			int returnValue = JOptionPane.showOptionDialog(null, "No connection to server!", "No connection", JOptionPane.OK_OPTION,
					JOptionPane.ERROR_MESSAGE, null, options, null);
			if (returnValue == 1) {
				viewGameController.retryConnectToServer();
			}
		}

		Throw newShot = viewGameController.getNextShot();
		if (newShot != null) {
			GraphicalSnowball sn = new GraphicalSnowball(newShot.getAngle(), newShot.getStength());
			board.fire(sn);
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		viewGameController.closeProgram();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}