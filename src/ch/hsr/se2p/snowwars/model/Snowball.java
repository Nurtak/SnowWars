package ch.hsr.se2p.snowwars.model;

public class Snowball extends ShotObject{
    
    private static final long serialVersionUID = -7432399665707233393L;
    
    private double damageMultiplier = 1.0;
    private int weight;
    
    public Snowball(int weight){
        this.weight = weight;
    }
    
    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getDamage() {
        return (int) Math.round(weight * damageMultiplier);
    }
    
}