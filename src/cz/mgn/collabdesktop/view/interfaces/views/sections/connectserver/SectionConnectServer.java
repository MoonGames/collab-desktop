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

import cz.mgn.collabdesktop.view.interfaces.views.Section;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public interface SectionConnectServer extends Section {

    /**
     * Shows dialog that client trying connect to server and disable other
     * actions (user only close this operation).
     */
    public void showConnectingToServer();

    /**
     * Dispose dialog that user client trying connect to server.
     */
    public void disposeConnectionToServer();

    /**
     * Shows advanced properties.
     */
    public void showAdvancedProperties();

    /**
     * Shows only basic properties.
     */
    public void showBasicProperties();

    /**
     * Refresh lobby informations.
     */
    public void refreshLobby(ServersLobby lobby);

    /**
     * Shows this nick in nick field.
     */
    public void setNick(String nick);

    /**
     * Shows this server address in server address field.
     */
    public void setServerAddress(String address);

    /**
     * Shows this server port in server port field.
     */
    public void setServerPort(int port);
}
