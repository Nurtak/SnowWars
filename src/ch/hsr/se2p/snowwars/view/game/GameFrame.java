package ch.hsr.se2p.snowwars.view.game;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.controller.game.GraphicalObject;
import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.model.Throw;

public class GameFrame extends JFrame implements Observer, WindowListener {
	private final static Logger logger = Logger.getLogger(GameFrame.class.getPackage().getName());
	private static final long serialVersionUID = -7803629994015778818L;

	private Board board;

	private final ViewGameController viewGameController;

	public GameFrame(ViewGameController vgc) {
		this.viewGameController = vgc;
		initializeGui();
		vgc.addObserver(this);
	}

	private void initializeGui() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setMinimumSize(new Dimension(viewGameController.getGameWidth(), viewGameController.getGameHeight()));
		setLocationRelativeTo(null);
		setTitle(viewGameController.getGameTitle());
		setResizable(false);
		setVisible(true);

		try {
			board = new Board(this);
			add(board);
		} catch (Exception e) {
			logger.error(e.getMessage());
			setVisible(false);
		}

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

		board.repaint();
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

	protected ArrayList<GraphicalObject> getGraphicalObjects() {
		return viewGameController.getGraphicalObjects();
	}
}