package ch.hsr.se2p.snowwars.view.lobby;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class UserPanel extends JPanel{
	private static final long serialVersionUID = -4628393851839832247L;

	private final ViewMain vm;
	
	public UserPanel(final ViewMain vm){
		this.vm = vm;
		createUserPanel();
	}
	
	private void createUserPanel(){
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton playButton = new JButton("Play");
		playButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				vm.nextCard();
			}
		});
		GridBagConstraints gbc_playButton = new GridBagConstraints();
		gbc_playButton.fill = GridBagConstraints.BOTH;
		gbc_playButton.insets = new Insets(0, 0, 5, 5);
		gbc_playButton.gridx = 1;
		gbc_playButton.gridy = 1;
		add(playButton, gbc_playButton);
		
		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				vm.exit();
			}
		});
		GridBagConstraints gbc_exitButton = new GridBagConstraints();
		gbc_exitButton.fill = GridBagConstraints.BOTH;
		gbc_exitButton.insets = new Insets(0, 0, 5, 5);
		gbc_exitButton.gridx = 1;
		gbc_exitButton.gridy = 2;
		add(exitButton, gbc_exitButton);

	}
}
