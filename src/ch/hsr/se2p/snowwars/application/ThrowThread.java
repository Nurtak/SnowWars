package ch.hsr.se2p.snowwars.application;

import java.util.Scanner;

import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;

/*
 * This Thread is used for creating shots from this client
 */

public class ThrowThread extends Thread{
	
	private RunRMIClient rmiClient;
	
	public ThrowThread(RunRMIClient rmiClient){
		this.rmiClient = rmiClient;
	}
	
	public void run(){
		while(true){
			Scanner scanner = new Scanner(System.in);
			
			System.out.println("Throw a shot? Press y or n and ENTER");
			String answer = scanner.next();
			if(answer.equals("n")){
				break;
			}
			if(!answer.equals("y")){
				continue;
			}
			
			System.out.println("Please enter an angle:");
			int angle = scanner.nextInt();
			
			System.out.println("Please enter the strength:");
			int strength = scanner.nextInt();
			
			System.out.println("Please enter the Weight");
			int weight = scanner.nextInt();
			
			Shot shot = new Shot(angle, strength, weight);
			rmiClient.throwShot(shot);
		}
	}
}
