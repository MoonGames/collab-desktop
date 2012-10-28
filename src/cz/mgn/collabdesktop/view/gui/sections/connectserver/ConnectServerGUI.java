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

package cz.mgn.collabdesktop.view.gui.sections.connectserver;

import cz.mgn.collabdesktop.controller.interfaces.connectserver.ConnectServerData;
import cz.mgn.collabdesktop.utils.settings.Settings;
import cz.mgn.collabdesktop.view.gui.sections.GUISection;
import cz.mgn.collabdesktop.view.gui.utils.FormUtility;
import cz.mgn.collabdesktop.view.interfaces.views.ServerException;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.SectionConnectServer;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.ServersLobby;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.ServersLobbyServer;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ConnectServerGUI extends GUISection implements ActionListener, SectionConnectServer {

    protected JTextField serverOption;
    protected JTextField nickOption;
    protected JCheckBox hostServer;
    protected JComboBox lobbyCombo;
    protected JButton lobbyInfo;
    protected JButton buttonConnect;
    protected String serverAddressBackUp = "";
    protected int height = 135;

    public ConnectServerGUI() {
        if (Settings.isLobbyEnabled) {
            height += 30;
        }
    }

    @Override
    public Dimension getOuterSize() {
        return new Dimension(400, height);
    }

    @Override
    public Dimension getMinimalSize() {
        return getOuterSize();
    }

    @Override
    public boolean isResizable() {
        return false;
    }

    @Override
    public String getSectionName() {
        return "connect server";
    }

    @Override
    public void initComponents() {
        nickOption = new JTextField(Settings.defaultNick);
        nickOption.addActionListener(this);
        serverOption = new JTextField(Settings.defaultServer);
        serverOption.addActionListener(this);
        hostServer = new JCheckBox("host server");
        hostServer.addActionListener(this);
        buttonConnect = new JButton("Connect");
        buttonConnect.addActionListener(this);
        lobbyCombo = new JComboBox();
        lobbyCombo.setEditable(false);
        lobbyCombo.addActionListener(this);
        lobbyInfo = new JButton("Info");
        lobbyInfo.addActionListener(this);

        setLayout(new BorderLayout());
        FormUtility formUtility = new FormUtility(new Insets(2, 2, 2, 2));
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(new EmptyBorder(8, 5, 8, 5));
        add(form, BorderLayout.NORTH);

        formUtility.addLabel("Nick: ", form);
        formUtility.addLastField(nickOption, form);

        if (Settings.isLobbyEnabled) {
            formUtility.addLabel("Lobby: ", form);
            JPanel lobbyHelpPanel = new JPanel(new BorderLayout(5, 0));
            lobbyHelpPanel.add(lobbyCombo, BorderLayout.CENTER);
            lobbyHelpPanel.add(lobbyInfo, BorderLayout.EAST);
            formUtility.addLastField(lobbyHelpPanel, form);
        }

        formUtility.addLabel("Server: ", form);
        JPanel serverHelpPanel = new JPanel(new BorderLayout(5, 0));
        serverHelpPanel.add(serverOption, BorderLayout.CENTER);
        serverHelpPanel.add(hostServer, BorderLayout.EAST);
        formUtility.addLastField(serverHelpPanel, form);

        formUtility.addLabel("", form);
        formUtility.addLastField(buttonConnect, form);

        if (Settings.isLobbyEnabled) {
            controller.getConnectServerInterface().refreshLobby();
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
            if (selectedItem != null && selectedItem instanceof ServersLobbyServer) {
                serverOption.setText(((ServersLobbyServer) selectedItem).getAddress());
            }
        } else if (source == lobbyInfo) {
            lobbyInfo();
        }
    }

    protected void lobbyInfo() {
        Object selectedItem = lobbyCombo.getSelectedItem();
        if (selectedItem != null && selectedItem instanceof ServersLobbyServer) {
            ServerInfoDialog dialog = new ServerInfoDialog((ServersLobbyServer) selectedItem);
            int x = getLocationOnScreen().x + (getWidth() / 2);
            int y = getLocationOnScreen().y + (getHeight() / 2);
            x = Math.max(0, x - (dialog.getWidth() / 2));
            y = Math.max(0, y - (dialog.getHeight() / 2));
            dialog.setLocation(x, y);
            dialog.setVisible(true);
        }
    }

    protected void connect() {
        final String address = serverOption.getText();
        final String nick = nickOption.getText();
        if (!nick.isEmpty()) {
            if (hostServer.isSelected()) {
                controller.getConnectServerInterface().hostServer(Settings.defaultPort);
            } else if (!address.isEmpty()) {
                controller.getConnectServerInterface().connectServer(new ConnectServerData() {
                    @Override
                    public int getPort() {
                        return Settings.defaultPort;
                    }

                    @Override
                    public String getAddress() {
                        return address;
                    }

                    @Override
                    public String getNick() {
                        return nick;
                    }
                });
            }
        }
    }

    protected void hostServerAction() {
        boolean host = hostServer.isSelected();
        serverOption.setEditable(!host);
        lobbyCombo.setEnabled(!host);
        lobbyInfo.setEnabled(!host);
        if (host) {
            serverAddressBackUp = serverOption.getText();
            serverOption.setText("");
        } else {
            serverOption.setText(serverAddressBackUp);
        }
    }

    @Override
    public void showConnectingToServer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disposeConnectionToServer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showAdvancedProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void showBasicProperties() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refreshLobby(ServersLobby lobby) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setNick(String nick) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setServerAddress(String address) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setServerPort(int port) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
