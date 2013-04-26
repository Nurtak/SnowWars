package ch.hsr.se2p.snowwars.view.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import ch.hsr.se2p.snowwars.controller.game.GraphicalObject;
import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.model.Snowball;
import ch.hsr.se2p.snowwars.model.Throw;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;

public class Board extends JPanel implements MouseListener {
	private static final long serialVersionUID = -2949809536472598850L;

	private final GameFrame gameFrame;

	private BufferedImage backgroundImage;

	private boolean mousePre;
	private int mousePreX;
	private int mousePreY;

	public Board(GameFrame vg) throws IOException {
		this.gameFrame = vg;

		BufferedImageLoader bil = BufferedImageLoader.getInstance();
		backgroundImage = bil.getBackgroundImage();

		addMouseListener(this);
		setFocusable(true);
		setDoubleBuffered(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(backgroundImage, 0, 0, null);

		ArrayList<GraphicalObject> graphicalObjectsList = gameFrame.getGraphicalObjects();
		synchronized (graphicalObjectsList) {
			for (GraphicalObject go : graphicalObjectsList) {
				g2d.drawImage(go.getImage(), go.getX(), go.getY(), this);
			}
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		mousePre = true;
		mousePreX = (int) arg0.getPoint().getX();
		mousePreY = (int) arg0.getPoint().getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (mousePre) {
			int x = (int) (this.mousePreX - arg0.getPoint().getX());
			int y = (int) (arg0.getPoint().getY() - this.mousePreY);

			int angle = (int) Math.toDegrees(Math.atan2(y, x));
			int strength = (int) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
			strength = strength / ViewGameController.FORCE_REDUCE_FACTOR_STRENGTH;

			mousePre = false;
			Snowball sb = new Snowball(10);
			Throw swthrow = new Throw(angle, strength, sb);

			startNewThrowRequest(swthrow);
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	public void startNewThrowRequest(Throw throwRequest) {
		gameFrame.newShotRequest(throwRequest);
	}
}
