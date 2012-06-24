/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.utils.homestorage;

import java.io.File;

/**
 *
 *      @author indy
 */
public class HomeStorage {

    protected static final String COLLAB_HOME_DIRECTORY = ".moongames/collab/collabdesktop";

    public static File getHomeFolder() {
        File home = new File(System.getProperty("user.home") + "/" + COLLAB_HOME_DIRECTORY);
        if (!home.exists()) {
            home.mkdirs();
        }
        return home;
    }
}
