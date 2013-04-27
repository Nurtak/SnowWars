package ch.hsr.se2p.snowwars.model;

public class Player {

    private User user;
    private int hitPoints;

    public Player(User user) {
        this(user, 100);
    }

    public Player(User user, int lifePoints) {
        this.user = user;
        this.hitPoints = lifePoints;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @return the hitPoints
     */
    public int getHitPoints() {
        return hitPoints;
    }

    /**
     * @param hitPoints
     *            the hitPoints to set
     */
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

}
