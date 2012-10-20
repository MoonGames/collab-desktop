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

package cz.mgn.collabdesktop;

import cz.mgn.collabdesktop.menu.frames.ConnectServer;
import cz.mgn.collabdesktop.utils.settings.SettingsIO;

/**
 *
 *   @author Martin Indra <aktive@seznam.cz>
 */
public class CollabDesktop {

    /**
     *   @param args the command line arguments
     */
    public static void main(String[] args) {
        new CollabDesktop();
    }
    protected int index = 0;

    public CollabDesktop() {
        SettingsIO.loadSettings();
        new ConnectServer().setVisible(true);
    }
}
