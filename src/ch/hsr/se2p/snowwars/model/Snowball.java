package ch.hsr.se2p.snowwars.model;

import java.io.Serializable;

public class Snowball implements ThrowingObject, Serializable{
 
    private static final long serialVersionUID = 1224522921433676069L;
    
    private double damageMultiplier = 1.0;
    private int weight;
    
    public Snowball(int weight){
        this.weight = weight;
    }

    @Override
    public int getDamageValue() {
        return (int) (weight * damageMultiplier);
    }

    @Override
    public int getWeight() {
        return weight;
    }
}