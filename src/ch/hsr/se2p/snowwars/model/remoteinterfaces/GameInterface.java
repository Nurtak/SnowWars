package ch.hsr.se2p.snowwars.model.remoteinterfaces;

import java.rmi.Remote;

import ch.hsr.se2p.snowwars.model.Throw;

public interface GameInterface extends Remote {

    public void throwShot (Throw shot);
}
