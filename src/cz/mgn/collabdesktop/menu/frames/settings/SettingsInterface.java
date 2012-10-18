/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.menu.frames.settings;

/**
 *
 * @author indy
 */
public interface SettingsInterface {

    /**
     * set all GUI visible properties from Collab variables
     */
    public void resetAll();

    /**
     * set all properties from GUI to Collab variables
     */
    public void setAll();
}
