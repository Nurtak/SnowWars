package ch.hsr.se2p.snowwars.model;

import java.io.Serializable;

public abstract class ShotObject implements Serializable{

    private static final long serialVersionUID = -4934533719382550831L;

    public abstract int getWeight();

    public abstract int getDamage();
    
}
