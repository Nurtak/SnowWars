package ch.hsr.se2p.snowwars.view.lobby.presentation;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;
import ch.hsr.se2p.snowwars.view.lobby.controlling.ClientLobbyController;
import ch.hsr.se2p.snowwars.view.lobby.controlling.ClientLobbyModel;

public class ClientViewMain extends JFrame implements Observer, WindowListener, ClientViewMainInterface {

	private final static Logger logger = Logger.getLogger(ClientViewMain.class.getPackage().getName());
	private static final long serialVersionUID = 7390513127049817797L;
	private ClientLobbyModel clientLobbyModel;
	private ClientLobbyController clientLobbyController;

	private JPanel contentPanel;
	private CardLayout cardLayout;

	public ClientViewMain(ClientLobbyModel clientLobbyModel, ClientLobbyController clientLobbyController) {
		getContentPane().setBackground(new Color(32, 145, 210));
		this.clientLobbyModel = clientLobbyModel;
		this.clientLobbyController = clientLobbyController;

		logger.info("Starting GUI...");
		createFrame();
		createLogoPanel();
		createContentPanel();

		pack();
		setSize(400, 500);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	private void createFrame() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		logger.info("Displaying ViewMain...");
		setTitle("Snow Wars");
		addWindowListener(this);
		createKeyBindings();
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

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
			logoPanel.setBackground(Color.WHITE);
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
			logger.error(e.getMessage(), e);
		}
	}

	private void createContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setBackground(new Color(32, 145, 210));
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
		mainPanel.setBackground(new Color(25, 145, 210));
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

	private PanelInterface getActivePane() {
		Component activeComponent = null;
		for (Component comp : contentPanel.getComponents()) {
			if (comp.isVisible()) {
				activeComponent = comp;
			}
		}
		return (PanelInterface) activeComponent;
	}

	public void nextCard() {
		cardLayout.next(contentPanel);
	}

	@Override
	public void previousCard() {
		cardLayout.previous(contentPanel);
	}

	public void exit() {
		logger.info("exiting via GUI");
		System.exit(0);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	}

	@Override
	public void leaveLobby() {
		try {
			logger.info("Leaving Lobby...");
			this.clientLobbyController.leaveLobby();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		} catch (SnowWarsRMIException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		leaveLobby();
		exit();
	}

	@Override
	public void windowActivated(WindowEvent arg0) {
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
	}
}
