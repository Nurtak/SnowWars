package ch.hsr.se2p.snowwars.view.lobby;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.controller.lobby.ViewLobbyController;
import ch.hsr.se2p.snowwars.controller.lobby.ViewLobbyModel;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.UserIsNotInLobbyException;

public class PanelLobby extends JPanel {
    private static final long serialVersionUID = -4628393851839832247L;
    private final static Logger logger = Logger.getLogger(PanelLobby.class.getPackage().getName());
    private final ViewMain vm;
    private ViewLobbyModel viewLobbyModel;
    private ViewLobbyController viewLobbyController;

    public PanelLobby(ViewMain vm, ViewLobbyModel viewLobbyModel, ViewLobbyController viewLobbyController) {
        this.vm = vm;
        this.viewLobbyModel = viewLobbyModel;
        this.viewLobbyController = viewLobbyController;
        createMainPanel();
    }

    private void createMainPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblUsername = new JLabel("Your Username: " + viewLobbyModel.getUser().getName());
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.gridwidth = 2;
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.insets = new Insets(0, 40, 5, 40);
        gbc_lblUsername.gridx = 0;
        gbc_lblUsername.gridy = 0;
        add(lblUsername, gbc_lblUsername);

        final JList<User> lstUsers = new JList<User>();
        lstUsers.setBorder(new LineBorder(new Color(0, 0, 0)));

        DefaultListModel<User> testModel = new DefaultListModel<User>();
        Set<User> usersToDisplay;

        usersToDisplay = viewLobbyModel.getUsers();
        for (User userToDisplay : usersToDisplay) {
            if (!userToDisplay.equals(viewLobbyModel.getUser())) {
                testModel.addElement(userToDisplay);
            }
        }
        lstUsers.setModel(testModel);

        GridBagConstraints gbc_lstUsers = new GridBagConstraints();
        gbc_lstUsers.gridwidth = 2;
        gbc_lstUsers.insets = new Insets(0, 40, 0, 40);
        gbc_lstUsers.fill = GridBagConstraints.BOTH;
        gbc_lstUsers.gridx = 0;
        gbc_lstUsers.gridy = 1;
        add(lstUsers, gbc_lstUsers);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                vm.previousCard();
            }
        });
        GridBagConstraints gbc_backButton = new GridBagConstraints();
        gbc_backButton.anchor = GridBagConstraints.WEST;
        gbc_backButton.insets = new Insets(5, 40, 5, 40);
        gbc_backButton.gridx = 0;
        gbc_backButton.gridy = 2;
        add(backButton, gbc_backButton);

        JButton inviteButton = new JButton("Invite");
        inviteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                logger.info("Invite Player...");
                try {
                    viewLobbyController.inviteUser(lstUsers.getSelectedValue());
                } catch (RemoteException | UserIsNotInLobbyException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        GridBagConstraints gbc_inviteButton = new GridBagConstraints();
        gbc_inviteButton.anchor = GridBagConstraints.EAST;
        gbc_inviteButton.insets = new Insets(5, 0, 5, 40);
        gbc_inviteButton.gridx = 1;
        gbc_inviteButton.gridy = 2;
        add(inviteButton, gbc_inviteButton);
    }
}
