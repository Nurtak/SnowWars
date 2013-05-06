package ch.hsr.se2p.snowwars.view.lobby;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ch.hsr.se2p.snowwars.controller.lobby.ViewLobbyController;
import ch.hsr.se2p.snowwars.controller.lobby.ViewLobbyModel;
import ch.hsr.se2p.snowwars.model.User;

public class PanelUser extends JPanel {
    private static final long serialVersionUID = -4628393851839832247L;
    private final ViewMain vm;
    private ViewLobbyModel viewLobbyModel;
    private ViewLobbyController viewLobbyController;
    private JTextField txtUsername;

    public PanelUser(ViewMain vm, ViewLobbyModel viewLobbyModel, ViewLobbyController viewLobbyController) {
        this.vm = vm;
        this.viewLobbyModel = viewLobbyModel;
        this.viewLobbyController = viewLobbyController;
        createUserPanel();
    }

    private void createUserPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 26, 186, 0, 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JLabel lblUsername = new JLabel("Username:");
        GridBagConstraints gbc_lblUsername = new GridBagConstraints();
        gbc_lblUsername.anchor = GridBagConstraints.WEST;
        gbc_lblUsername.gridwidth = 3;
        gbc_lblUsername.insets = new Insets(0, 40, 5, 0);
        gbc_lblUsername.gridx = 1;
        gbc_lblUsername.gridy = 0;
        add(lblUsername, gbc_lblUsername);

        txtUsername = new JTextField();
        GridBagConstraints gbc_txtUsername = new GridBagConstraints();
        gbc_txtUsername.gridwidth = 3;
        gbc_txtUsername.insets = new Insets(0, 40, 5, 0);
        gbc_txtUsername.fill = GridBagConstraints.HORIZONTAL;
        gbc_txtUsername.gridx = 1;
        gbc_txtUsername.gridy = 1;
        add(txtUsername, gbc_txtUsername);
        txtUsername.setColumns(10);

        JLabel lblError = new JLabel("Platzhalter Error-Meldungen");
        lblError.setForeground(Color.RED);
        GridBagConstraints gbc_lblError = new GridBagConstraints();
        gbc_lblError.gridwidth = 3;
        gbc_lblError.anchor = GridBagConstraints.WEST;
        gbc_lblError.insets = new Insets(0, 40, 5, 5);
        gbc_lblError.gridx = 1;
        gbc_lblError.gridy = 2;
        add(lblError, gbc_lblError);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                vm.previousCard();
            }
        });
        GridBagConstraints gbc_backButton = new GridBagConstraints();
        gbc_backButton.anchor = GridBagConstraints.WEST;
        gbc_backButton.insets = new Insets(0, 40, 0, 5);
        gbc_backButton.gridx = 1;
        gbc_backButton.gridy = 3;
        add(backButton, gbc_backButton);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (viewLobbyController.isNameAvailable(txtUsername.getText())) {
                        User user = new User(txtUsername.getText());
                        viewLobbyController.registerAtLobby(user);
                        vm.addPanel(new PanelLobby(vm, viewLobbyModel, viewLobbyController), "lobbyPanel");
                        vm.nextCard();
                    }
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }              
            }
        });
        GridBagConstraints gbc_playButton = new GridBagConstraints();
        gbc_playButton.anchor = GridBagConstraints.EAST;
        gbc_playButton.gridx = 3;
        gbc_playButton.gridy = 3;
        add(playButton, gbc_playButton);

    }
}
