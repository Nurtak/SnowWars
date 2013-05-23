package ch.hsr.se2p.snowwars.view.game;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.viewcontrolling.game.ViewGameController;
import ch.hsr.se2p.snowwars.viewcontrolling.game.ViewGameModel;

public class GameFrame extends JFrame implements Observer, WindowListener, GameFrameInterface {
	private final static Logger logger = Logger.getLogger(GameFrame.class.getPackage().getName());
	private static final long serialVersionUID = -7803629994015778818L;

	private Board board;

	private final ViewGameController viewGameController;
	private final ViewGameModel viewGameModel;

	public GameFrame(ViewGameController vgc, ViewGameModel vgm) {
		this.viewGameController = vgc;
		this.viewGameModel = vgm;
		initializeGui();
		vgm.addObserver(this);
	}

	private void initializeGui() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setMinimumSize(new Dimension(getViewGameModel().getGameWidth(), getViewGameModel().getGameHeight()));
		setLocationRelativeTo(null);
		setTitle(getViewGameModel().getGameTitle());
		setResizable(false);

		try {
			board = new Board(this);
			add(board);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			setVisible(false);
		}

		this.pack();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		board.repaint();
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		getViewGameController().quitGame();
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

	protected ViewGameController getViewGameController() {
		return viewGameController;
	}

	@Override
	public ViewGameModel getViewGameModel() {
		return viewGameModel;
	}
}
