package ch.hsr.se2p.snowwars.view.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import ch.hsr.se2p.snowwars.model.Throw;

public class Board extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = -2949809536472598850L;
	private final static int TIMER_REDRAW_INTERVAL = 10;
	private final static String BACKGROUND_IMAGE_PATH = "img/background.jpg";

	protected final static int GROUND_LEVEL_Y = 420;

	protected final static int FORCE_REDUCE_FACTOR = 15;
	private final static int FORCE_REDUCE_FACTOR_STRENGTH = 2;
	protected final static double GRAVITATION = 9.81 / 50;

	private final ViewGame viewGame;

	private ArrayList<GraphicalSnowball> snowballs;

	private BufferedImage backgroundImage;

	private Timer timer;

	private Player player;

	private boolean mousePre;
	private int mousePreX;
	private int mousePreY;

	public Board(ViewGame vg) {
		this.viewGame = vg;

		addMouseListener(this);
		setFocusable(true);
		setDoubleBuffered(true);

		snowballs = new ArrayList<GraphicalSnowball>();

		player = new Player();

		timer = new Timer(TIMER_REDRAW_INTERVAL, this);
		timer.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_IMAGE_PATH));
			g2d.drawImage(backgroundImage, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}

		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

		synchronized (this) {
			for (GraphicalSnowball s : snowballs) {
				g2d.drawImage(s.getImage(), s.getX(), s.getY(), this);
			}
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public synchronized void actionPerformed(ActionEvent e) {
		for (GraphicalSnowball s : snowballs) {
			if (s.isVisible()) {
				s.move();
			}

			repaint();
		}

		player.move();
		repaint();
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
			strength = strength / FORCE_REDUCE_FACTOR_STRENGTH;

			mousePre = false;
			// Snowball sn = new Snowball(angle, strength);
			Throw shot = new Throw(angle, strength, 10);
			startNewShotRequest(shot);
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

	public void startNewShotRequest(Throw shot) {
		viewGame.newShotRequest(shot);
	}

	public synchronized void fire(GraphicalSnowball snowBall) {
		snowballs.add(snowBall);
	}
}