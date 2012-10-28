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

import cz.mgn.collabdesktop.model.utils.lobbyutil.ServerLobby;
import cz.mgn.collabdesktop.utils.settings.Settings;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.ServersLobby;
import cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver.ServersLobbyServer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ServersLobbyAdapter implements ServersLobby {

    protected Set<ServersLobbyServer> servers = new HashSet<ServersLobbyServer>();

    public ServersLobbyAdapter(ArrayList<ServerLobby> servers) {
        for (ServerLobby server : servers) {
            this.servers.add(
                    new ServerLobbyAdapterServer(server.getName(), server.getAddress(), server.getDescription()));
        }
    }

    @Override
    public Set<ServersLobbyServer> getServersSet() {
        return servers;
    }

    @Override
    public boolean isLobbyEmpty() {
        return servers.isEmpty();
    }

    public static class ServerLobbyAdapterServer implements ServersLobbyServer {

        protected String name;
        protected String address;
        protected String description;

        public ServerLobbyAdapterServer(String name, String address, String description) {
            this.name = name;
            this.address = address;
            this.description = description;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getDescription() {
            return description;
        }

        @Override
        public String getAddress() {
            return address;
        }

        @Override
        public int getPort() {
            return Settings.defaultPort;
        }

        @Override
        public int getType() {
            return ServersLobbyServer.TYPE_PUBLIC;
        }

        @Override
        public boolean isVerified() {
            return false;
        }
    }
}
