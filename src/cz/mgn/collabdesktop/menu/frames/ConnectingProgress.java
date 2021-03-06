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
import cz.mgn.collabdesktop.network.Client;
import cz.mgn.collabdesktop.network.ConnectionInterface;
import cz.mgn.collabdesktop.network.commands.CommandGenerator;
import java.awt.*;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 *          @author Martin Indra <aktive@seznam.cz>
 */
public class ConnectingProgress extends MenuFrame implements ConnectionInterface {

    protected Client client;
    protected String nick = "";
    protected String address;
    protected Set<ConnectionInterface> connectionInterfaces;

    public ConnectingProgress(String nick, String address, Set<ConnectionInterface> connectionInterfaces) {
        super();
        this.connectionInterfaces = connectionInterfaces;
        this.nick = nick;
        this.address = address;
        connect(address);
    }

    protected void connect(String address) {
        client = new Client(address);
        client.addConnectionInterface(this);
        for(ConnectionInterface connectionInterface : connectionInterfaces) {
            client.addConnectionInterface(connectionInterface);
        }
        client.start();
    }

    @Override
    protected String getSectionName() {
        return "connecting to server";
    }

    @Override
    protected void initComponents() {
        Container pane = getContentPane();
        pane.setLayout(new GridBagLayout());

        JLabel label = new JLabel("Connecting to server...");
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(label, c);

        Insets in = getInsets();
        Dimension size = new Dimension(400, 80);
        size.width += in.left + in.right;
        size.height += in.top + in.bottom;
        setPreferredSize(size);
        setSize(getPreferredSize());
        setResizable(false);
    }

    protected void noConnection() {
        goTo(new ConnectServer(address), false);
    }

    @Override
    public void connectionError(Client client) {
        noConnection();
        client.removeConnectionInterface(this);
    }

    @Override
    public void connectionSuccessful(Client client) {
        client.send(CommandGenerator.generateSetNickCommand(nick));
        client.removeConnectionInterface(this);
        goTo(new ChooseRoom(client), false);
    }

    @Override
    public void connectionClosed(Client client) {
        noConnection();
        client.removeConnectionInterface(this);
    }
}
