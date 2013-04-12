package ch.hsr.se2p.snowwars.view.lobby;

import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.RunSnowWarsClient;

public class ViewMain extends JFrame{
	private final static Logger logger = Logger.getLogger(ViewMain.class.getPackage().getName());

	private static final long serialVersionUID = 7390513127049817797L;

	private final RunSnowWarsClient swc;
	
	public ViewMain(RunSnowWarsClient swc){
		this.swc = swc;
		
		logger.info("Displaying ViewMain...");
		setTitle("SnowWars");
		
		getContentPane().add(new MainPanel(this));
		
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	protected void clicked(){
		getContentPane().add(new JLabel("bla"));
	}
}
