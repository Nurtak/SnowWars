package ch.hsr.se2p.snowwars.view.lobby.presentation;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;
import ch.hsr.se2p.snowwars.view.FontLoader;
import ch.hsr.se2p.snowwars.view.lobby.controlling.ClientLobbyController;
import ch.hsr.se2p.snowwars.view.lobby.controlling.ClientLobbyModel;

public class PanelLobby extends JPanel implements Observer, PanelInterface {
    private static final long serialVersionUID = -4628393851839832247L;
    private final static Logger logger = Logger.getLogger(PanelLobby.class.getPackage().getName());
    private final ClientViewMainInterface cvm;
    private ClientLobbyModel clientLobbyModel;
    private JLabel lblUsernameDescription;
    private ClientLobbyController clientLobbyController;
    private JList<User> lstUsers;
    DefaultListModel<User> lobbyModel = new DefaultListModel<User>();
    private JScrollPane scrollPane;
    private JLabel lblLobbyDescription;
    private JLabel lblUsername;
    public BufferedImageLoader loader;

    public PanelLobby(ClientViewMainInterface cvm, ClientLobbyModel clientLobbyModel, ClientLobbyController clientLobbyController) {
        setBackground(new Color(32, 145, 210));
        this.cvm = cvm;
        this.clientLobbyModel = clientLobbyModel;
        this.clientLobbyController = clientLobbyController;
        clientLobbyModel.addObserver(this);
        createMainPanel();
    }

    private void createMainPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 170, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        lblUsernameDescription = new JLabel("Your Username: ");
        lblUsernameDescription.setForeground(new Color(255, 255, 255));
        lblUsernameDescription.setFont(FontLoader.getInstance().getGameFont(18));
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.insets = new Insets(0, 40, 5, 0);
        gbc_lblUsername.gridx = 0;
        gbc_lblUsername.gridy = 1;
        add(lblUsernameDescription, gbc_lblUsername);

        DefaultListModel<User> testModel = new DefaultListModel<User>();

        Set<User> usersToDisplay;
        usersToDisplay = clientLobbyModel.getUsers();
        for (User userToDisplay : usersToDisplay) {
            if (!userToDisplay.equals(clientLobbyModel.getUser())) {
                testModel.addElement(userToDisplay);
            }
        }

        lblUsername = new JLabel(clientLobbyModel.getUser().getName());
        lblUsername.setFont(FontLoader.getInstance().getGameFont(18));
        lblUsername.setForeground(new Color(255, 255, 255));
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.gridwidth = 2;
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 1;
        add(lblUsername, gbc_lblNewLabel);

        lblLobbyDescription = new JLabel("Available users:");
        lblLobbyDescription.setFont(FontLoader.getInstance().getGameFont(12));
        lblLobbyDescription.setForeground(new Color(255, 255, 255));
        GridBagConstraints gbc_lblUsersDieSich = new GridBagConstraints();
        gbc_lblUsersDieSich.anchor = GridBagConstraints.WEST;
        gbc_lblUsersDieSich.gridwidth = 3;
        gbc_lblUsersDieSich.insets = new Insets(0, 40, 5, 5);
        gbc_lblUsersDieSich.gridx = 0;
        gbc_lblUsersDieSich.gridy = 2;
        add(lblLobbyDescription, gbc_lblUsersDieSich);

        scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(0, 40, 5, 40);
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 3;
        add(scrollPane, gbc_scrollPane);
        lstUsers = new JList<User>();
        scrollPane.setViewportView(lstUsers);
        lstUsers.setBorder(null);
        lstUsers.setModel(testModel);

        JButton backButton = new JButton();
        backButton.setIcon(new ImageIcon(PanelLobby.class.getResource("/img/backButton.gif")));
        backButton.setBorder(null);
        backButton.setBackground(new Color(25, 145, 210));
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                backPressed();
            }
        });
        GridBagConstraints gbc_backButton = new GridBagConstraints();
        gbc_backButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_backButton.insets = new Insets(5, 40, 0, 40);
        gbc_backButton.gridx = 0;
        gbc_backButton.gridy = 4;
        add(backButton, gbc_backButton);

        JButton inviteButton = new JButton();
        inviteButton.setIcon(new ImageIcon(PanelLobby.class.getResource("/img/inviteButton.gif")));
        inviteButton.setBorder(null);
        inviteButton.setBackground(new Color(25, 145, 210));
        inviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                invitePressed();
            }
        });
        GridBagConstraints gbc_inviteButton = new GridBagConstraints();
        gbc_inviteButton.anchor = GridBagConstraints.NORTHEAST;
        gbc_inviteButton.insets = new Insets(5, 0, 0, 40);
        gbc_inviteButton.gridx = 2;
        gbc_inviteButton.gridy = 4;
        add(inviteButton, gbc_inviteButton);
    }

    @Override
    public void update(Observable o, Object arg) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Here, we can safely update the GUI
                // because we'll be called from the
                // event dispatch thread
                lobbyModel.clear();
                Set<User> usersToDisplay = clientLobbyModel.getUsers();
                for (User userToDisplay : usersToDisplay) {
                    if (!userToDisplay.equals(clientLobbyModel.getUser())) {
                        lobbyModel.addElement(userToDisplay);
                    }
                }
                lstUsers.setModel(lobbyModel);
            }
        });
    }

    private void invitePressed() {
        try {
            User selectedUser = lstUsers.getSelectedValue();
            if (selectedUser != null) {
                logger.info("Invite Player...");
                clientLobbyController.inviteUser(lstUsers.getSelectedValue());
            }
        } catch (RemoteException | UserIsNotInLobbyException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void backPressed() {
        cvm.leaveLobby();
        cvm.previousCard();
    }

    @Override
    public void enterPressed() {
        invitePressed();
    }

    @Override
    public void escPressed() {
        backPressed();
    }
}
