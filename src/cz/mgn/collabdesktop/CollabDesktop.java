/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop;

import cz.mgn.collabdesktop.menu.frames.ConnectServer;
import cz.mgn.collabdesktop.utils.settings.SettingsIO;

/**
 *
 *   @author indy
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
