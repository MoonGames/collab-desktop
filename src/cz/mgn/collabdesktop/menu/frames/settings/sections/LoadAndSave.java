/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.menu.frames.settings.sections;

import cz.mgn.collabdesktop.menu.frames.settings.SettingsInterface;
import cz.mgn.collabdesktop.menu.frames.settings.SettingsPanel;
import cz.mgn.collabdesktop.utils.settings.SettingsIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author indy
 */
public class LoadAndSave extends SettingsPanel implements ActionListener {

    protected SettingsInterface settingsInterface;
    protected JButton load;
    protected JButton save;

    public LoadAndSave(SettingsInterface settingsInterface) {
        super();
        this.settingsInterface = settingsInterface;

        formUtility.addLastField(load = new JButton("Re-load"), form);
        formUtility.addLastField(save = new JButton("Save"), form);

        load.addActionListener(this);
        save.addActionListener(this);
    }

    @Override
    public String getPanelName() {
        return "Load & save";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == load) {
            SettingsIO.loadSettings();
            settingsInterface.resetAll();
        } else if (e.getSource() == save) {
            settingsInterface.setAll();
            SettingsIO.writeSettings();
            settingsInterface.resetAll();
        }
    }

    @Override
    public void reset() {
    }

    @Override
    public void set() {
    }
}
