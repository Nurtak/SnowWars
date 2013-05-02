package ch.hsr.se2p.snowwars.controller.lobby;

import java.util.Observable;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;

public class ViewLobbyController extends Observable{
    
    private SnowWarsClient snowWarsClient;
	
	public ViewLobbyController(SnowWarsClient snowWarsClient){
	    this.snowWarsClient = snowWarsClient;
	}
	
	

}
