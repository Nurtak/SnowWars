package ch.hsr.se2p.snowwars.controller.game;

import java.awt.Image;

public abstract class GraphicalObject {	
	public abstract int getX();
	public abstract int getY();
	public abstract Image getImage();
	protected abstract void updateValues();
}
