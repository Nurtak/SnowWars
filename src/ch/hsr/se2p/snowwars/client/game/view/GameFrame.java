package ch.hsr.se2p.snowwars.client.game.view;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.client.game.applicationcontrolling.ViewGameController;
import ch.hsr.se2p.snowwars.client.game.viewmodel.ViewGameModel;

public class GameFrame extends JFrame implements Observer, WindowListener, GameFrameInterface {
	private final static Logger logger = Logger.getLogger(GameFrame.class.getPackage().getName());
	private static final long serialVersionUID = -7803629994015778818L;

	private final ViewGameController viewGameController;
	private final ViewGameModel viewGameModel;
	private Board board;
	
	public GameFrame(ViewGameController viewGameController, ViewGameModel viewGameModel) {
		this.viewGameController = viewGameController;
		this.viewGameModel = viewGameModel;
		initializeGui();
		viewGameModel.addObserver(this);
	}

	private void initializeGui() {
        setTitle(viewGameModel.getViewTitle());
        setName(viewGameModel.getViewName());
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setMinimumSize(new Dimension(viewGameModel.getGameWidth(), viewGameModel.getGameHeight()));
		setLocationRelativeTo(null);
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
