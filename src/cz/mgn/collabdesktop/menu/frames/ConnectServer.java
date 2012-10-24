/*
 * Collab desktop - Software for shared drawing via internet in real-time
 * Copyright (C) 2012 Martin Indra <aktive@seznam.cz>
 *
 * This file is part of Collab desktop.
 *
 * Collab desktop is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Collab desktop is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Collab desktop.  If not, see <http://www.gnu.org/licenses/>.
 */
package cz.mgn.collabdesktop.menu.frames;

import cz.mgn.collabdesktop.menu.MenuFrame;
import cz.mgn.collabdesktop.utils.gui.FormUtility;
import cz.mgn.collabdesktop.utils.lobbyutil.LobbyListener;
import cz.mgn.collabdesktop.utils.lobbyutil.LobbyUtil;
import cz.mgn.collabdesktop.utils.lobbyutil.ServerLobby;
import cz.mgn.collabdesktop.utils.settings.Settings;
import cz.mgn.collabserver.CollabServer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 *
 *   @author Martin Indra <aktive@seznam.cz>
 */
public class ConnectServer extends MenuFrame implements ActionListener, LobbyListener {

    protected JTextField serverOption;
    protected JTextField nickOption;
    protected JCheckBox hostServer;
    protected JComboBox lobbyCombo;
    protected JButton lobbyInfo;
    protected JButton buttonConnect;
    protected String serverAddressBackUp = "";

    public ConnectServer() {
        super();
        centerWindow();
        init();
    }

    public ConnectServer(String address) {
        super();
        centerWindow();
        serverOption.setText(address);
        init();
    }

    private void init() {
        if (Settings.isLobbyEnabled) {
            LobbyUtil.loadLobby(this);
        }
    }

    @Override
    protected String getSectionName() {
        return "connect server";
    }

    @Override
    protected void initComponents() {
        initMenuBar();

        int aditionalHeight = 0;

        nickOption = new JTextField(Settings.defaultNick);
        nickOption.addActionListener(this);
        serverOption = new JTextField(Settings.defaultServer);
        serverOption.addActionListener(this);
        hostServer = new JCheckBox("host server");
        hostServer.addActionListener(this);
        buttonConnect = new JButton("Connect");
        buttonConnect.addActionListener(this);
        lobbyCombo = new JComboBox(LobbyUtil.getServers().toArray());
        lobbyCombo.setEditable(false);
        lobbyCombo.addActionListener(this);
        lobbyInfo = new JButton("Info");
        lobbyInfo.addActionListener(this);

        getContentPane().setLayout(new BorderLayout());
        FormUtility formUtility = new FormUtility(new Insets(2, 2, 2, 2));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 5, 8, 5));
        getContentPane().add(form, BorderLayout.NORTH);

        formUtility.addLabel("Nick: ", form);
        formUtility.addLastField(nickOption, form);

        if (Settings.isLobbyEnabled) {
            formUtility.addLabel("Lobby: ", form);
            JPanel lobbyHelpPanel = new JPanel(new BorderLayout(5, 0));
            lobbyHelpPanel.add(lobbyCombo, BorderLayout.CENTER);
            lobbyHelpPanel.add(lobbyInfo, BorderLayout.EAST);
            formUtility.addLastField(lobbyHelpPanel, form);
            aditionalHeight += 30;
        }

        formUtility.addLabel("Server: ", form);
        JPanel serverHelpPanel = new JPanel(new BorderLayout(5, 0));
        serverHelpPanel.add(serverOption, BorderLayout.CENTER);
        serverHelpPanel.add(hostServer, BorderLayout.EAST);
        formUtility.addLastField(serverHelpPanel, form);

        formUtility.addLabel("", form);
        formUtility.addLastField(buttonConnect, form);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 135 + aditionalHeight);
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setResizable(false);
    }

    protected void connect() {
        if (hostServer.isSelected()) {
            CollabServer.setLogLevel(CollabServer.LOG_LEVEL_ERROR);
            CollabServer.startServer(Settings.defaultPort);
            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String address = serverOption.getText();
        String nick = nickOption.getText();
        if (!address.isEmpty() && !nick.isEmpty()) {
            goTo(new ConnectingProgress(nick, address), false);
        }
    }

    protected void hostServerAction() {
        boolean host = hostServer.isSelected();
        serverOption.setEditable(!host);
        if (host) {
            serverAddressBackUp = serverOption.getText();
            try {
                serverOption.setText(InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(ConnectServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            serverOption.setText(serverAddressBackUp);
        }
    }

    protected void lobbyInfo() {
        Object selectedItem = lobbyCombo.getSelectedItem();
        if (selectedItem != null && selectedItem instanceof ServerLobby) {
            ServerInfoDialog dialog = new ServerInfoDialog(this, (ServerLobby) selectedItem);
            int x = getLocationOnScreen().x + (getWidth() / 2);
            int y = getLocationOnScreen().y + (getHeight() / 2);
            x = Math.max(0, x - (dialog.getWidth() / 2));
            y = Math.max(0, y - (dialog.getHeight() / 2));
            dialog.setLocation(x, y);
            dialog.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == nickOption || source == buttonConnect) {
            connect();
        } else if (source == hostServer) {
            hostServerAction();
        } else if (source == lobbyCombo) {
            Object selectedItem = lobbyCombo.getSelectedItem();
            if (selectedItem != null && selectedItem instanceof ServerLobby) {
                serverOption.setText(((ServerLobby) selectedItem).getAddress());
            }
        } else if (source == lobbyInfo) {
            lobbyInfo();
        }
    }

    @Override
    public void lobbyReceived(ArrayList<ServerLobby> servers) {
        lobbyCombo.removeAllItems();
        for (ServerLobby server : servers) {
            lobbyCombo.addItem(server);
        }
    }
}
