package ch.hsr.se2p.snowwars.model.remoteinterfaces;

import java.rmi.Remote;

public interface UserInterface extends Remote {

    /**
     * @return the name
     */
    public abstract String getName();

}
