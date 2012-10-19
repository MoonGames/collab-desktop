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

import cz.mgn.collabdesktop.menu.frames.settings.SettingsPanel;
import cz.mgn.collabdesktop.utils.settings.Settings;
import javax.swing.JTextField;

/**
 *
 * @author indy
 */
public class Room extends SettingsPanel {

    protected JTextField defaultRoomName;
    protected JTextField defaultWidth;
    protected JTextField defaultHeight;
    protected JTextField maximalWidth;
    protected JTextField maximalHeight;

    public Room() {
        super();
        
        formUtility.addLabel("Default room name: ", form);
        formUtility.addLastField(defaultRoomName = new JTextField(), form);

        formUtility.addLabel("Default width [px]: ", form);
        formUtility.addLastField(defaultWidth = new JTextField(), form);

        formUtility.addLabel("Default height [px]: ", form);
        formUtility.addLastField(defaultHeight = new JTextField(), form);

        formUtility.addLabel("Maximal width [px]: ", form);
        formUtility.addLastField(maximalWidth = new JTextField(), form);
        
        formUtility.addLabel("Maximal height [px]: ", form);
        formUtility.addLastField(maximalHeight = new JTextField(), form);

        reset();
    }

    @Override
    public String getPanelName() {
        return "Room";
    }

    @Override
    public void reset() {
        defaultRoomName.setText(Settings.defaultRoomName);
        defaultWidth.setText("" + Settings.defaultRoomWidth);
        defaultHeight.setText("" + Settings.defaultRoomHeight);
        maximalWidth.setText("" + Settings.maximalRoomWidth);
        maximalHeight.setText("" + Settings.maximalRoomHeight);
    }

    @Override
    public void set() {
        Settings.defaultRoomName = defaultRoomName.getText();
        try {
            int mWidth = Integer.parseInt(maximalWidth.getText());
            if (mWidth > 0 && mWidth < 4096) {
                Settings.maximalRoomWidth = mWidth;
            }
        } catch (NumberFormatException e) {
        }
        try {
            int mHeight = Integer.parseInt(maximalHeight.getText());
            if (mHeight > 0 && mHeight < 4096) {
                Settings.maximalRoomHeight = mHeight;
            }
        } catch (NumberFormatException e) {
        }
        
        try {
            int dWidth = Integer.parseInt(defaultWidth.getText());
            dWidth = Math.min(dWidth, Settings.maximalRoomWidth);
            if (dWidth > 0 && dWidth < 4096) {
                Settings.defaultRoomWidth = dWidth;
            }
        } catch (NumberFormatException e) {
        }
        try {
            int dHeight = Integer.parseInt(defaultHeight.getText());
            dHeight = Math.min(dHeight, Settings.maximalRoomHeight);
            if (dHeight > 0 && dHeight < 4096) {
                Settings.defaultRoomHeight = dHeight;
            }
        } catch (NumberFormatException e) {
        }
        
    }
}
