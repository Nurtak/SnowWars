package ch.hsr.se2p.snowwars.model.client;

import ch.hsr.se2p.snowwars.model.remoteinterfaces.PlayerInterface;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.UserInterface;

public class Player implements PlayerInterface {
	public final static int MAX_HEALTH_POINTS = 100;
	
	private UserInterface user;
	private int hitPoints;
	
	public Player(UserInterface user) {
		this(user, 100);
	}

	public Player(UserInterface user, int lifePoints) {
		this.user = user;
		this.hitPoints = lifePoints;
	}

	/* (non-Javadoc)
     * @see ch.hsr.se2p.snowwars.model.PlayerInterface#getUser()
     */
	@Override
    public UserInterface getUser() {
		return user;
	}

	/* (non-Javadoc)
     * @see ch.hsr.se2p.snowwars.model.PlayerInterface#getHitPoints()
     */
	@Override
    public int getHitPoints() {
		return hitPoints;
	}

	/* (non-Javadoc)
     * @see ch.hsr.se2p.snowwars.model.PlayerInterface#setHitPoints(int)
     */
	@Override
    public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}
}
