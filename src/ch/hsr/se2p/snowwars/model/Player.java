package ch.hsr.se2p.snowwars.model;


public class Player {
    public final static int MAX_HIT_POINTS = 100;

    private User user;
    private int hitPoints;

    public Player(User user) {
        this(user, 100);
    }

    public Player(User user, int lifePoints) {
        this.user = user;
        this.hitPoints = lifePoints;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.hsr.se2p.snowwars.model.PlayerInterface#getUser()
     */
    public User getUser() {
        return user;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.hsr.se2p.snowwars.model.PlayerInterface#getHitPoints()
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.hsr.se2p.snowwars.model.PlayerInterface#setHitPoints(int)
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }
}
