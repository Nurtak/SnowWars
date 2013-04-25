package ch.hsr.se2p.snowwars.controller.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.view.BufferedImageLoader;

public class GraphicalSnowball extends GraphicalObject {
	private final static Logger logger = Logger.getLogger(GraphicalSnowball.class.getPackage().getName());

	private int x,y;
	private double dy, dx;
	private Image snowballImage, snowballCrashedImage;
	boolean visible;

	public GraphicalSnowball(int angle, int strength) {
		BufferedImageLoader bil = BufferedImageLoader.getInstance();
		BufferedImage snowFlakeII;
		try {
			snowFlakeII = bil.getSnowballImage();
			BufferedImage snowFlakeIICrashed = bil.getSnowballCrashedImage();

			snowballImage = new ImageIcon(snowFlakeII).getImage();
			snowballCrashedImage = new ImageIcon(snowFlakeIICrashed).getImage();

			visible = true;

			this.x = GraphicalPlayer.PLAYER_LEFT_POSITION_X + 50;
			this.y = GraphicalPlayer.PLAYER_LEFT_POSITION_Y + 50;

			double vySin = Math.sin(Math.toRadians(angle));
			double vxCos = Math.cos(Math.toRadians(angle));

			this.dy = (int) (vySin * strength) * -1;
			this.dx = (int) (vxCos * strength);

			this.dy = this.dy / ViewGameController.FORCE_REDUCE_FACTOR;
			this.dx = this.dx / ViewGameController.FORCE_REDUCE_FACTOR;
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public Image getImage() {
		if (visible) {
			return snowballImage;
		} else {
			return snowballCrashedImage;
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void updateValues() {
		this.dy += ViewGameController.GRAVITATION;

		this.x = (int) ((int) this.x + this.dx);
		this.y = (int) ((int) this.y + this.dy);

		if (x > ViewGameController.GAME_WIDTH || y > ViewGameController.GROUND_LEVEL_Y) {
			visible = false;
		}
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}
}
