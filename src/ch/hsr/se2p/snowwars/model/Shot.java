package ch.hsr.se2p.snowwars.model;

public class Shot {
	private final int angle;
	private final int strength;
	private final int weight;
	
	public Shot(int angle, int strength, int weight){
		this.angle = angle;
		this.strength = strength;
		this.weight = weight;
	}
	
	public int getAngle() {
		return angle;
	}

	public int getStength() {
		return strength;
	}

	public int getWeight() {
		return weight;
	}
	
	@Override
	public String toString(){
		return "Angle(" + angle + ") Strength(" + strength + ") Weight(" + weight + ")";
	}
}