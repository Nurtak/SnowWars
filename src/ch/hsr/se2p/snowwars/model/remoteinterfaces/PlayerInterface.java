package ch.hsr.se2p.snowwars.model.remoteinterfaces;

import java.rmi.Remote;


public interface PlayerInterface extends Remote{

    /**
     * @return the user
     */
    public abstract UserInterface getUser();

    /**
     * @return the hitPoints
     */
    public abstract int getHitPoints();

    /**
     * @param hitPoints
     *           the hitPoints to set
     */
    public abstract void setHitPoints(int hitPoints);

}
