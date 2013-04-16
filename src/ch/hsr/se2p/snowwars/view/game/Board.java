package ch.hsr.se2p.snowwars.view.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = -487520520612533276L;
	private Timer timer;
	private Player player;

	private boolean mousePre;
	private int mousePreX;
	private int mousePreY;
	private BufferedImage background;

	public Board() {
		addKeyListener(new TAdapter());
		addMouseListener(this);
		setFocusable(true);
		// setBackground(new ImageIcon("background.jpg"));
		setDoubleBuffered(true);

		player = new Player();

		timer = new Timer(10, this);
		timer.start();

	}

	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		try {
			background = ImageIO.read(this.getClass().getResource("img/background.jpg"));
			g2d.drawImage(background, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2d.drawImage(player.getImage(), player.getX(), player.getY(), this);

		ArrayList<Snowball> snowballs = player.getSnowballs();

		for (int i = 0; i < snowballs.size(); i++) {
			Snowball s = (Snowball) snowballs.get(i);
			g2d.drawImage(s.getImage(), s.getX(), s.getY(), this);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		ArrayList<Snowball> snowballs = player.getSnowballs();

		for (int i = 0; i < snowballs.size(); i++) {
			Snowball s = (Snowball) snowballs.get(i);
			if (s.isVisible())
				s.move();
			repaint();
		}

		player.move();
		repaint();
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			player.keyPressed(e);
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

			// double relation = y / x;
			int angle = (int) Math.toDegrees(Math.atan2(y, x));
			int strength = (int) (Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
			strength = strength / 3;

			System.out.println("angle: " + angle + ", strength: " + strength);

			System.out.println("x: " + x + ", y: " + y);
			mousePre = false;
			Snowball sn = new Snowball(110, 350, angle, strength);
			player.fire(sn);
		}
	}
}