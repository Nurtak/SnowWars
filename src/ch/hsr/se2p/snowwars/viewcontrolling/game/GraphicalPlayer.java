package ch.hsr.se2p.snowwars.viewcontrolling.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Player.PlayerState;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;

public class GraphicalPlayer extends GraphicalObject {
	private final static Logger logger = Logger.getLogger(GraphicalPlayer.class.getPackage().getName());

	private final int WIDTH = 136;
	private final int HEIGHT = 167;
	private final int WIDTH_BUILDING = 129;
	private final int HEIGHT_BUILDING = 146;

	public AnimationController throwingAnimation;
	public AnimationController standingAnimation;
	public AnimationController buildingAnimation;
	public AnimationController activeAnimation;

	private BufferedImage spriteSheet;
	private BufferedImage spriteSheetBendDown;
	public BufferedImageLoader loader;

	private Player player;

	public GraphicalPlayer(Player player) {
		this.player = player;
		PlayerPosition pos = player.getPosition();

		try {
			loader = BufferedImageLoader.getInstance();
			switch (pos) {
				case LEFT :
					spriteSheet = loader.getPlayerLeftSpriteSheet();
					spriteSheetBendDown = loader.getBendDownLeftPlayer();
					break;
				case RIGHT :
					spriteSheet = loader.getPlayerRightSpriteSheet();
					spriteSheetBendDown = loader.getBendDownLeftPlayer();
					break;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		loadThrowAnimation();
		loadStandingAnimation();
		loadBuildingAnimation();
		activeAnimation = standingAnimation;
	}

	private void loadThrowAnimation() {
		ArrayList<BufferedImage> spritesForThrow = new ArrayList<BufferedImage>();

		spritesForThrow.add(spriteSheet.getSubimage(0, 0, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(WIDTH, 0, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(2 * WIDTH, 0, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(0, HEIGHT, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(WIDTH, HEIGHT, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(2 * WIDTH, HEIGHT, WIDTH, HEIGHT));

		throwingAnimation = new AnimationController(spritesForThrow);
		throwingAnimation.setSpeed(100);
	}

	private void loadStandingAnimation() {
		ArrayList<BufferedImage> spritesForStand = new ArrayList<BufferedImage>();
		spritesForStand.add(spriteSheet.getSubimage(0, 0, WIDTH, HEIGHT));
		standingAnimation = new AnimationController(spritesForStand);
		standingAnimation.setSpeed(-1);
	}

	public void loadBuildingAnimation() {
		ArrayList<BufferedImage> spritesForBuild = new ArrayList<BufferedImage>();
		spritesForBuild.add(spriteSheetBendDown.getSubimage(0, 0, WIDTH_BUILDING, HEIGHT_BUILDING));
		spritesForBuild.add(spriteSheetBendDown.getSubimage(WIDTH_BUILDING, 0, WIDTH_BUILDING, HEIGHT_BUILDING));
		spritesForBuild.add(spriteSheetBendDown.getSubimage(2*WIDTH_BUILDING, 0, WIDTH_BUILDING, HEIGHT_BUILDING));
		buildingAnimation = new AnimationController(spritesForBuild);
		buildingAnimation.setSpeed(200);
	}

	@Override
	public int getX() {
		switch (this.player.getPosition()) {
			case LEFT :
				return Player.PLAYER_LEFT_POSITION_X;
			default :
				return Player.PLAYER_RIGHT_POSITION_X;
		}
	}

	@Override
	public int getY() {
		switch (this.player.getPosition()) {
			case LEFT :
				return Player.PLAYER_LEFT_POSITION_Y;
			default :
				return Player.PLAYER_RIGHT_POSITION_Y;
		}
	}

	@Override
	public Image getImage() {
		return activeAnimation.getSprite();
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public void updateAnimation() {
		PlayerState playerState = player.getPlayerState();

		switch (playerState) {
			case BUILDING :
				this.activeAnimation = buildingAnimation;
				break;
			case STANDING :
				this.activeAnimation = standingAnimation;
				break;
			case THROWING :
				this.activeAnimation = throwingAnimation;
				break;
		}

		try {
			activeAnimation.update(System.currentTimeMillis());
		} catch (Exception e) {
			if(player.getPlayerState() != PlayerState.BUILDING){
				activeAnimation = standingAnimation;
				this.player.setPlayerState(PlayerState.STANDING);
			}
		}
	}

}