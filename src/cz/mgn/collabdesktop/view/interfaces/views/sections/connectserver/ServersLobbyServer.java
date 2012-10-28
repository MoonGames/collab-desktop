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

package cz.mgn.collabdesktop.view.interfaces.views.sections.connectserver;

/**
 * This interface provide informations about server get from lobby.
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public interface ServersLobbyServer {

    /**
     * Permanently running server reachable from Internet.
     */
    public static int TYPE_PUBLIC = 0;
    /**
     * Temporary server and reachable only from LAN.
     */
    public static int TYPE_LAN = 1;

    /**
     * Returns name of server.
     */
    public String getName();

    /**
     * Identification of server (for example name (address)
     */
    @Override
    public String toString();

    /**
     * Returns description of server.
     */
    public String getDescription();

    /**
     * Returns server address.
     */
    public String getAddress();

    /**
     * Returns listening port of server.
     */
    public int getPort();

    /**
     * Returns type of server, see TYPE_PUBLIC and TYPE_LAN
     */
    public int getType();

    /**
     * Returns if server trusted by provider of lobby.
     */
    public boolean isVerified();
}
