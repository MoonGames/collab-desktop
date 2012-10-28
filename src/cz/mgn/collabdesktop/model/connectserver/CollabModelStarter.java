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

package cz.mgn.collabdesktop.model.connectserver;

import cz.mgn.collabdesktop.controller.interfaces.ModelInterface;
import cz.mgn.collabdesktop.controller.interfaces.connectserver.ConnectServerData;
import cz.mgn.collabdesktop.controller.interfaces.connectserver.ConnectServerInterface;
import cz.mgn.collabdesktop.model.network.Client;
import cz.mgn.collabdesktop.model.network.ConnectionInterface;
import cz.mgn.collabdesktop.model.utils.lobbyutil.LobbyListener;
import cz.mgn.collabdesktop.model.utils.lobbyutil.LobbyUtil;
import cz.mgn.collabdesktop.model.utils.lobbyutil.ServerLobby;
import cz.mgn.collabdesktop.utils.settings.Settings;
import cz.mgn.collabdesktop.utils.settings.SettingsIO;
import cz.mgn.collabdesktop.view.ViewFactory;
import cz.mgn.collabdesktop.view.interfaces.View;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.SectionConnectServer;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.ServersLobby;
import cz.mgn.collabserver.CollabServer;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class CollabModelStarter implements ModelInterface, ConnectServerInterface, ConnectionInterface {

    protected View view;
    protected SectionConnectServer viewConnectServer;
    protected Client client;

    public CollabModelStarter() {
        SettingsIO.loadSettings();
        view = ViewFactory.initView(this);
        viewConnectServer = view.switchToSectionConnectServer();
        viewConnectServer.showBasicProperties();
    }

    @Override
    public ConnectServerInterface getConnectServerInterface() {
        return this;
    }

    @Override
    public void connectServer(ConnectServerData data) {
        if (client == null) {
            client = new Client(data.getAddress(), data.getPort());
            client.addConnectionInterface(this);
            viewConnectServer.showConnectingToServer();
            client.start();
        }
    }

    @Override
    public void refreshLobby() {
        LobbyUtil.loadLobby(new LobbyListener() {
            @Override
            public void lobbyReceived(ArrayList<ServerLobby> servers) {
                ServersLobby serversLobby = new ServersLobbyAdapter(servers);
                viewConnectServer.refreshLobby(serversLobby);
            }
        });
    }

    @Override
    public void hostServer(int port) {
        if (client == null) {
            try {
                client = new Client(InetAddress.getLocalHost().getHostAddress(), port);

                CollabServer.setLogLevel(CollabServer.LOG_LEVEL_ERROR);
                CollabServer.startServer(port);
                ConnectionInterface serverConnectionInterface = new ConnectionInterface() {
                    @Override
                    public void connectionError(Client client) {
                        CollabServer.stopServer(Settings.defaultPort);
                        client.removeConnectionInterface(this);
                    }

                    @Override
                    public void connectionSuccessful(Client client) {
                    }

                    @Override
                    public void connectionClosed(Client client) {
                        CollabServer.stopServer(Settings.defaultPort);
                        client.removeConnectionInterface(this);
                    }
                };

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(CollabModelStarter.class.getName()).log(Level.SEVERE, null, ex);
                }

                client.addConnectionInterface(this);
                client.addConnectionInterface(serverConnectionInterface);
                viewConnectServer.showConnectingToServer();
                client.start();
            } catch (UnknownHostException ex) {
                Logger.getLogger(CollabModelStarter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected void onConnectionError(Client client) {
        client.removeConnectionInterface(this);
        viewConnectServer.disposeConnectionToServer();
        this.client = null;
    }

    @Override
    public void connectionError(Client client) {
        onConnectionError(client);
    }

    @Override
    public void connectionSuccessful(Client client) {
        //TODO: go to another section
    }

    @Override
    public void connectionClosed(Client client) {
        onConnectionError(client);
    }
}
