package ch.hsr.se2p.snowwars.view.lobby;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JPanel;

import ch.hsr.se2p.snowwars.viewcontrolling.lobby.ClientLobbyController;
import ch.hsr.se2p.snowwars.viewcontrolling.lobby.ClientLobbyModel;

public class PanelMain extends JPanel implements Observer, PanelInterface{

	private static final long serialVersionUID = -4628393851839832247L;
	private final ClientViewMainInterface cvm;
	private ClientLobbyModel clientLobbyModel;
	private ClientLobbyController clientLobbyController;

	public PanelMain(ClientViewMainInterface cvm, ClientLobbyModel clientLobbyModel, ClientLobbyController clientLobbyController) {
		this.cvm = cvm;
		this.clientLobbyModel = clientLobbyModel;
		this.clientLobbyController = clientLobbyController;
		clientLobbyModel.addObserver(this);
		createMainPanel();
	}

	private void createMainPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		JButton playButton = new JButton("Play");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playPressed();
			}
		});
		GridBagConstraints gbc_playButton = new GridBagConstraints();
		gbc_playButton.fill = GridBagConstraints.BOTH;
		gbc_playButton.insets = new Insets(0, 0, 5, 0);
		gbc_playButton.gridx = 0;
		gbc_playButton.gridy = 0;
		add(playButton, gbc_playButton);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exitPressed();
			}
		});
		GridBagConstraints gbc_exitButton = new GridBagConstraints();
		gbc_exitButton.fill = GridBagConstraints.BOTH;
		gbc_exitButton.gridx = 0;
		gbc_exitButton.gridy = 1;
		add(exitButton, gbc_exitButton);

	}

	private void playPressed() {
		cvm.addPanel(new PanelUser(cvm, clientLobbyModel, clientLobbyController), "userPanel");
		cvm.nextCard();
	}
	
	private void exitPressed() {
		cvm.exit();
	}
	
	@Override
	public void update(Observable o, Object arg) {
	}

	@Override
	public void enterPressed() {
		playPressed();
	}

	@Override
	public void escPressed() {
		escPressed();
	}
}
