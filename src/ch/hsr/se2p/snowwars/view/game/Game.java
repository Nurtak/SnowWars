package ch.hsr.se2p.snowwars.view.game;

import javax.swing.JFrame;

public class Game extends JFrame {
	private static final long serialVersionUID = -7803629994015778818L;

	public Game() {

		add(new Board());

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1000, 600);
		setLocationRelativeTo(null);
		setTitle("SnowWars");
		setResizable(false);
		setVisible(true);
	}
}
