package ch.hsr.se2p.snowwars.model.remoteinterfaces;

import java.rmi.Remote;

import ch.hsr.se2p.snowwars.model.Shot;

public interface GameInterface extends Remote {

    public void buildSnowball();

    public void throwShot(Shot shot);
}
