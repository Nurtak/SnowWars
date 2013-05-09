package ch.hsr.se2p.snowwars.view.game;

import java.awt.BasicStroke;
import java.awt.Color;
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
import ch.hsr.se2p.snowwars.model.AbstractGame;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.Snowball;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;
import ch.hsr.se2p.snowwars.view.game.PlayerInfoPanel.PlayerInfoPanelPosition;

public class Board extends JPanel implements MouseListener {
	private static final long serialVersionUID = -2949809536472598850L;

	private final GameFrame gameFrame;

	private BufferedImage backgroundImage;

	private boolean playerAiming;
	private int aimingStartX;
	private int aimingStartY;

	ArrayList<GraphicalObject> graphicalObjectsList = new ArrayList<GraphicalObject>();

	public Board(GameFrame vg) throws IOException {
		this.gameFrame = vg;

		BufferedImageLoader bil = BufferedImageLoader.getInstance();
		backgroundImage = bil.getBackgroundImage();

		SoundPlayer.getInstance().playWindSound();
		addMouseListener(this);
		setFocusable(true);
		setDoubleBuffered(true);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		paintBackground(g2d);
		paintGraphicalObjects(g2d);
		paintPlayerInfoPanel(g2d);
		paintAimingArrow(g2d);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	private void paintBackground(Graphics2D g2d) {
		g2d.drawImage(backgroundImage, 0, 0, null);
	}

	private void paintGraphicalObjects(Graphics2D g2d) {
		graphicalObjectsList.clear();
		graphicalObjectsList.add(gameFrame.getViewGameModel().getLeftPlayer());
		graphicalObjectsList.add(gameFrame.getViewGameModel().getRightPlayer());
		graphicalObjectsList.addAll(gameFrame.getViewGameModel().getGraphicalSnowballs());

		for (GraphicalObject go : graphicalObjectsList) {
			if (go.isVisible()) {
				g2d.drawImage(go.getImage(), go.getX(), go.getY(), this);
			}
		}
	}

	private void paintPlayerInfoPanel(Graphics2D g2d) {
		Player playerLeft = gameFrame.getViewGameModel().getGame().getPlayerLeft();
		Player playerRight = gameFrame.getViewGameModel().getGame().getPlayerRight();

		int gameWidth = gameFrame.getViewGameModel().getGameWidth();
		new PlayerInfoPanel(playerLeft, PlayerInfoPanelPosition.LEFT, gameWidth).paint(g2d);
		new PlayerInfoPanel(playerRight, PlayerInfoPanelPosition.RIGHT, gameWidth).paint(g2d);
	}

	private void paintAimingArrow(Graphics2D g2d) {
		if (playerAiming) {
			try {
				drawArrow(g2d, (int) this.getMousePosition().getX(), (int) this.getMousePosition().getY(), aimingStartX, aimingStartY);
			} catch (Exception e) {
			}
		}
	}

	private void drawArrow(Graphics2D g, int x, int y, int xx, int yy) {
		if (x == xx && y == yy) {
			return;
		}

		float arrowWidth = 25.0f;
		float theta = 1.0f;
		int[] xPoints = new int[3];
		int[] yPoints = new int[3];
		float[] vecLine = new float[2];
		float[] vecLeft = new float[2];
		float fLength;
		float th;
		float ta;
		float baseX, baseY;

		xPoints[0] = xx;
		yPoints[0] = yy;

		// build the line vector
		vecLine[0] = (float) xPoints[0] - x;
		vecLine[1] = (float) yPoints[0] - y;

		// build the arrow base vector - normal to the line
		vecLeft[0] = -vecLine[1];
		vecLeft[1] = vecLine[0];

		// setup length parameters
		fLength = (float) Math.sqrt(vecLine[0] * vecLine[0] + vecLine[1] * vecLine[1]);
		th = arrowWidth / (2.0f * fLength);
		ta = arrowWidth / (2.0f * ((float) Math.tan(theta) / 2.0f) * fLength);

		// find the base of the arrow
		baseX = ((float) xPoints[0] - ta * vecLine[0]);
		baseY = ((float) yPoints[0] - ta * vecLine[1]);

		// build the points on the sides of the arrow
		xPoints[1] = (int) (baseX + th * vecLeft[0]);
		yPoints[1] = (int) (baseY + th * vecLeft[1]);
		xPoints[2] = (int) (baseX - th * vecLeft[0]);
		yPoints[2] = (int) (baseY - th * vecLeft[1]);

		g.setColor(Color.red);
		g.setStroke(new BasicStroke(3));
		g.drawLine(x, y, (int) baseX, (int) baseY);
		g.fillPolygon(xPoints, yPoints, 3);
	}

	public void startNewThrowRequest(Shot shotRequest) {
		gameFrame.getViewGameModel().startNewShotRequest(shotRequest);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		playerAiming = true;
		aimingStartX = (int) arg0.getPoint().getX();
		aimingStartY = (int) arg0.getPoint().getY();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (playerAiming) {
			playerAiming = false;

			try {
				int x = (int) (this.aimingStartX - (int) this.getMousePosition().getX());
				int y = (int) ((int) this.getMousePosition().getY() - this.aimingStartY);

				int angle = (int) Math.toDegrees(Math.atan2(y, x));
				int strength = (int) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
				strength = strength / AbstractGame.FORCE_REDUCE_FACTOR_STRENGTH;

				Snowball sb = new Snowball(10);
				Shot shot = new Shot(angle, strength, sb);

				startNewThrowRequest(shot);
			} catch (Exception e) {
			}
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
}
