package ch.hsr.se2p.snowwars.exceptions;

public class SnowWarsRMIException extends Exception{	
    
    private static final long serialVersionUID = 1500691983893195404L;

	public SnowWarsRMIException(String errorMessage){
		super(errorMessage);
	}
}
