package ch.hsr.se2p.snowwars.view.lobby;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.CardLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class ViewMain extends JFrame{
	private final static Logger logger = Logger.getLogger(ViewMain.class.getPackage().getName());

	private static final long serialVersionUID = 7390513127049817797L;

	//private final RunSnowWarsClient swc;

	private JPanel contentPanel;
	private CardLayout cardLayout;
	
	public ViewMain(SnowWarsClient swc){
		//this.swc = swc;
	
		createFrame();
		createLogoPanel();
		createContentPanel();
		//getContentPane().add(new MainPanel(this));
	
		pack();
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	private void createFrame(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		logger.info("Displaying ViewMain...");
		setTitle("Snow Wars");
		createKeyBindings();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{124, 0};
		gridBagLayout.rowHeights = new int[]{10, 29, 0};
		gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
	}
	
	private void createLogoPanel(){
		ImageIcon snowWarsLogo = new ImageIcon("img/logo.png");
		
		JPanel logoPanel = new JPanel();
		logoPanel.setBorder(null);
		GridBagConstraints gbc_logoPanel = new GridBagConstraints();
		gbc_logoPanel.anchor = GridBagConstraints.NORTH;
		gbc_logoPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_logoPanel.insets = new Insets(0, 0, 5, 0);
		gbc_logoPanel.gridx = 0;
		gbc_logoPanel.gridy = 0;
		getContentPane().add(logoPanel, gbc_logoPanel);
		JLabel imageLabel = new JLabel(snowWarsLogo);
		logoPanel.add(imageLabel);
	}
	
	private void createContentPanel(){
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
		
		JPanel mainPanel = new PanelMain(this);
		JPanel userPanel = new PanelUser(this);
		JPanel  lobbyPanel = new PanelLobby(this);
		
      contentPanel.add(mainPanel, "mainPanel");
      contentPanel.add(userPanel, "userPanel");
      contentPanel.add(lobbyPanel, "lobbyPanel");
	}
	
	private void createKeyBindings(){
		KeyboardFocusManager kfm = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		kfm.addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				switch (e.getID()) {
				case KeyEvent.KEY_PRESSED:
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						nextCard();
					} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
						previousCard();
					}
					break;
				}
				return false;
			}
		});
	}
	
	public void nextCard(){
		cardLayout.next(contentPanel);
	}
	
	public void previousCard(){
		cardLayout.previous(contentPanel);
	}
	
	public void exit(){
		System.exit(0);
	}
}
