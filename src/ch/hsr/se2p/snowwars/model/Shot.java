package ch.hsr.se2p.snowwars.model;

import java.io.Serializable;

public class Shot implements Serializable {
    private static final long serialVersionUID = 1528366581031974029L;

    private final int angle;
    private final int strength;
    private ShotObject throwingObject;

    public Shot(int angle, int strength, ShotObject throwingObject) {
        this.angle = angle;
        this.strength = strength;
        this.throwingObject = throwingObject;
    }

    public int getAngle() {
        return angle;
    }

    public int getStrength() {
        return strength;
    }

    public int getWeight() {
        return throwingObject.getWeight();
    }

    public int getDamage(){
        return throwingObject.getDamage();
    }
    
    @Override
    public String toString() {
        return "Angle(" + angle + ") " + "Strength(" + strength + ") " + "Weight(" + throwingObject.getWeight() + ") " + "DamageValue("
                + throwingObject.getDamage() + ")";
    }
}