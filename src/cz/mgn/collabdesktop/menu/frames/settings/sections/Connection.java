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
package cz.mgn.collabdesktop.menu.frames.settings.sections;

import cz.mgn.collabdesktop.menu.frames.settings.SettingsPanel;
import cz.mgn.collabdesktop.utils.settings.Settings;
import java.awt.BorderLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class Connection extends SettingsPanel {

    protected JTextField defaultNick;
    protected JTextField defaultServer;
    protected JTextField defaultPort;
    protected JCheckBox isLobbyEnabled;
    protected JTextField lobbyURL;

    public Connection() {
        super();

        formUtility.addLabel("Default nick: ", form);
        formUtility.addLastField(defaultNick = new JTextField(), form);

        formUtility.addLabel("Default server: ", form);
        formUtility.addLastField(defaultServer = new JTextField(), form);

        formUtility.addLabel("Connection port: ", form);
        formUtility.addLastField(defaultPort = new JTextField(), form);

        formUtility.addLabel("Lobby source URL: ", form);
        JPanel lobbyHelpPanel = new JPanel(new BorderLayout(5, 0));
        lobbyHelpPanel.add(lobbyURL = new JTextField(), BorderLayout.CENTER);
        lobbyHelpPanel.add(isLobbyEnabled = new JCheckBox("enabled"), BorderLayout.EAST);
        formUtility.addLastField(lobbyHelpPanel, form);

        reset();
    }

    @Override
    public String getPanelName() {
        return "Connection";
    }

    @Override
    public void reset() {
        defaultNick.setText(Settings.defaultNick);
        defaultServer.setText(Settings.defaultServer);
        defaultPort.setText("" + Settings.defaultPort);
        lobbyURL.setText(Settings.lobbySourceURL);
        isLobbyEnabled.setSelected(Settings.isLobbyEnabled);
    }

    @Override
    public void set() {
        Settings.defaultNick = defaultNick.getText();
        Settings.defaultServer = defaultServer.getText();
        try {
            int port = Integer.parseInt(defaultPort.getText());
            if (port > 0 && port < 65536) {
                Settings.defaultPort = port;
            }
        } catch (NumberFormatException e) {
        }
        Settings.isLobbyEnabled = isLobbyEnabled.isSelected();
        Settings.lobbySourceURL = lobbyURL.getText();
    }

    @Override
    public boolean isChanged() {
        int port = Settings.defaultPort;
        try {
            port = Integer.parseInt(defaultPort.getText());
        } catch (NumberFormatException e) {
        }
        return !Settings.defaultNick.equals(defaultNick.getText())
                || !Settings.defaultServer.equals(defaultServer.getText())
                || (Settings.defaultPort != port)
                || (Settings.isLobbyEnabled != isLobbyEnabled.isSelected())
                || !Settings.lobbySourceURL.equals(lobbyURL.getText());
    }
}
