package ch.hsr.se2p.snowwars.view.lobby;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;
import ch.hsr.se2p.snowwars.viewcontrolling.lobby.ClientLobbyController;
import ch.hsr.se2p.snowwars.viewcontrolling.lobby.ClientLobbyModel;

public class ClientViewMain extends JFrame implements Observer, ClientViewMainInterface {

	private final static Logger logger = Logger.getLogger(ClientViewMain.class.getPackage().getName());
	private static final long serialVersionUID = 7390513127049817797L;
	private ClientLobbyModel clientLobbyModel;
	private ClientLobbyController clientLobbyController;

	private JPanel contentPanel;
	private CardLayout cardLayout;

	public ClientViewMain(ClientLobbyModel clientLobbyModel, ClientLobbyController clientLobbyController) {
		this.clientLobbyModel = clientLobbyModel;
		this.clientLobbyController = clientLobbyController;

		logger.info("Starting GUI...");
		createFrame();
		createLogoPanel();
		createContentPanel();

		pack();
		setSize(400, 400);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void createFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logger.info("Displaying ViewMain...");
		setTitle("SnowWars");
		createKeyBindings();

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{124, 0};
		gridBagLayout.rowHeights = new int[]{10, 29, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}

	private void createLogoPanel() {
		BufferedImageLoader bufferedImageLoader = BufferedImageLoader.getInstance();
		BufferedImage logoImage;
		try {
			logoImage = bufferedImageLoader.getLogoImage();

			JPanel logoPanel = new JPanel();
			logoPanel.setBorder(null);
			GridBagConstraints gbc_logoPanel = new GridBagConstraints();
			gbc_logoPanel.anchor = GridBagConstraints.NORTH;
			gbc_logoPanel.fill = GridBagConstraints.HORIZONTAL;
			gbc_logoPanel.insets = new Insets(0, 0, 5, 0);
			gbc_logoPanel.gridx = 0;
			gbc_logoPanel.gridy = 0;
			getContentPane().add(logoPanel, gbc_logoPanel);
			JLabel imageLabel = new JLabel(new ImageIcon(logoImage));
			logoPanel.add(imageLabel);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	private void createContentPanel() {
		contentPanel = new JPanel();
		GridBagConstraints gbc_contentPanel = new GridBagConstraints();
		gbc_contentPanel.insets = new Insets(5, 5, 5, 5);
		gbc_contentPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_contentPanel.anchor = GridBagConstraints.NORTH;
		gbc_contentPanel.gridx = 0;
		gbc_contentPanel.gridy = 1;
		getContentPane().add(contentPanel, gbc_contentPanel);

		cardLayout = new CardLayout();
		contentPanel.setLayout(cardLayout);

		JPanel mainPanel = new PanelMain(this, clientLobbyModel, clientLobbyController);
		contentPanel.add(mainPanel, "mainPanel");
	}

	@Override
	public void addPanel(JPanel jPanel, String name) {
		contentPanel.add(jPanel, name);
	}

	private void createKeyBindings() {
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {				
				switch (e.getID()) {
					case KeyEvent.KEY_PRESSED :
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							getActivePane().enterPressed();
						} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
							getActivePane().escPressed();
						}
						break;
				}
				return false;
			}
		});

	}
	
	private PanelInterface getActivePane(){
		Component activeComponent = null;
		for (Component comp : contentPanel.getComponents()) {
			if (comp.isVisible()) {
				activeComponent = comp;
			}
		}
		return (PanelInterface)activeComponent;
	}

	//

	public void nextCard() {
		cardLayout.next(contentPanel);
	}

	@Override
	public void previousCard() {
		cardLayout.previous(contentPanel);
	}

	public void exit() {
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	}

	@Override
	public void leaveLobby() {
		try {
			this.clientLobbyController.leaveLobby();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (SnowWarsRMIException e) {
			e.printStackTrace();
		}
	}
}
