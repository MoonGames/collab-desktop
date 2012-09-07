/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.menu.frames.settings.sections;

import cz.mgn.collabdesktop.menu.frames.settings.SettingsPanel;
import javax.swing.JButton;

/**
 *
 *  @author indy
 */
public class LoadAndSave extends SettingsPanel {
    
    public LoadAndSave() {
        super();
        
        addProperty("Load:", new JButton("Load"));
        addProperty("Save:", new JButton("Save"));
    }

    @Override
    public String getPanelName() {
        return "Load & save";
    }
}
