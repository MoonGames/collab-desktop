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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.menu.frames.settings.sections;

import cz.mgn.collabdesktop.menu.frames.settings.SettingsPanel;
import cz.mgn.collabdesktop.utils.settings.Settings;
import javax.swing.JTextField;

/**
 *
 * @author indy
 */
public class Connection extends SettingsPanel {

    protected JTextField defaultNick;
    protected JTextField defaultServer;
    protected JTextField defaultPort;

    public Connection() {
        super();

        formUtility.addLabel("Default nick: ", form);
        formUtility.addLastField(defaultNick = new JTextField(), form);

        formUtility.addLabel("Default server: ", form);
        formUtility.addLastField(defaultServer = new JTextField(), form);

        formUtility.addLabel("Connection port: ", form);
        formUtility.addLastField(defaultPort = new JTextField(), form);

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
    }
}
