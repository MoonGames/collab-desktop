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
