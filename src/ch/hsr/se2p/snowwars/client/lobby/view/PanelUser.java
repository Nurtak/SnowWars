package ch.hsr.se2p.snowwars.client.lobby.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.client.lobby.applicationcontrolling.ClientLobbyController;
import ch.hsr.se2p.snowwars.client.lobby.viewmodel.ClientLobbyModel;
import ch.hsr.se2p.snowwars.client.viewutils.FontLoader;

public class PanelUser extends JPanel implements Observer, PanelInterface {
    private static final long serialVersionUID = -4628393851839832247L;
    private final static Logger logger = Logger.getLogger(PanelLobby.class.getPackage().getName());
    private final ClientViewMainInterface cvm;
    private ClientLobbyModel viewLobbyModel;
    private ClientLobbyController viewLobbyController;
    private JTextField txtUsername;

    public PanelUser(ClientViewMainInterface cvm, ClientLobbyModel clientLobbyModel, ClientLobbyController clientLobbyController) {
        setBackground(new Color(32, 145, 210));
        this.cvm = cvm;
        this.viewLobbyModel = clientLobbyModel;
        this.viewLobbyController = clientLobbyController;
        clientLobbyModel.addObserver(this);
        createUserPanel();
    }

    private void createUserPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 26, 186, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(new Color(255, 255, 255));
        lblUsername.setFont(FontLoader.getInstance().getGameFont(17));
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.gridwidth = 3;
        gbc_lblUsername.insets = new Insets(0, 40, 5, 0);
        gbc_lblUsername.gridx = 1;
        gbc_lblUsername.gridy = 1;
        add(lblUsername, gbc_lblUsername);

        txtUsername = new JTextField();
        txtUsername.setName("txtUsername");
        GridBagConstraints gbc_txtUsername = new GridBagConstraints();
        gbc_txtUsername.gridwidth = 3;
        gbc_txtUsername.insets = new Insets(0, 38, 5, 5);
        gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUsername.gridx = 1;
        gbc_txtUsername.gridy = 2;
        add(txtUsername, gbc_txtUsername);
        txtUsername.setColumns(10);

        JButton backButton = new JButton();
        backButton.setName("backButton");
        backButton.setBackground(new Color(25, 145, 210));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backPressed();
            }
        });
        GridBagConstraints gbc_backButton = new GridBagConstraints();
        backButton.setIcon(new ImageIcon(PanelLobby.class.getResource("/img/backButton.gif")));
        backButton.setBorder(null);
        gbc_backButton.anchor = GridBagConstraints.WEST;
        gbc_backButton.insets = new Insets(0, 40, 0, 5);
        gbc_backButton.gridx = 1;
        gbc_backButton.gridy = 4;
        add(backButton, gbc_backButton);

        JButton playButton = new JButton();
        playButton.setName("playButton");
        playButton.setIcon(new ImageIcon(PanelLobby.class.getResource("/img/playButton.gif")));
        playButton.setBorder(null);
        playButton.setBackground(new Color(25, 145, 210));
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                playPressed();
            }
        });
        GridBagConstraints gbc_playButton = new GridBagConstraints();
        gbc_playButton.anchor = GridBagConstraints.EAST;
        gbc_playButton.gridx = 3;
        gbc_playButton.gridy = 4;
        add(playButton, gbc_playButton);
    }

    private void backPressed() {
        cvm.previousCard();
    }

    private void playPressed() {
        try {
            if (txtUsername.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter an username!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (txtUsername.getText().length() > 25) {
                JOptionPane.showMessageDialog(this, "Username is too long! (>25 characters)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (viewLobbyController.isNameAvailable(txtUsername.getText())) {
                viewLobbyController.registerAtLobby(txtUsername.getText());
                cvm.addPanel(new PanelLobby(cvm, viewLobbyModel, viewLobbyController), "lobbyPanel");
                cvm.nextCard();
            } else {
                JOptionPane.showMessageDialog(this, "Username is already taken!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }
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
        backPressed();
    }
}
