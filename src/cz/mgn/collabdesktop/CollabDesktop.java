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
import cz.mgn.collabdesktop.utils.settings.Settings;
import cz.mgn.collabdesktop.utils.settings.SettingsIO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class CollabDesktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        processArgs(args);
        new CollabDesktop();
    }

    protected static void processArgs(String[] args) {
        final String PARAM_PORT = "--port";
        final String PARAM_SERVER = "--server";
        String lastArg = "";

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (PARAM_PORT.equals(lastArg)) {
                try {
                    Settings.defaultPort = Integer.parseInt(arg);
                    Settings.forcePort = true;
                } catch (NumberFormatException ex) {
                    System.err.println("Invalid port parameter! (" + arg + ")");
                }
            } else if (PARAM_SERVER.equals(lastArg)) {
                Settings.defaultServer = arg;
                Settings.forceServer = true;
            }
            lastArg = arg;
        }
    }

    public CollabDesktop() {
        try {
            // Use the same look and feel on every platform
            UIManager.setLookAndFeel(
                    UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CollabDesktop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(CollabDesktop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(CollabDesktop.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(CollabDesktop.class.getName()).log(Level.SEVERE, null, ex);
        }
        SettingsIO.loadSettings();
        new ConnectServer().setVisible(true);
    }
}
