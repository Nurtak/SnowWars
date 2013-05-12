package ch.hsr.se2p.snowwars.viewcontrolling.game;

import java.awt.Image;

public abstract class GraphicalObject {
	public abstract int getX();

	public abstract int getY();

	public abstract Image getImage();

	public abstract boolean isVisible();

	public abstract void updateAnimation();
}
