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
package cz.mgn.collabdesktop.menu.frames.settings;

import cz.mgn.collabdesktop.menu.MenuFrame;
import cz.mgn.collabdesktop.menu.frames.settings.sections.Connection;
import cz.mgn.collabdesktop.menu.frames.settings.sections.LoadAndSave;
import cz.mgn.collabdesktop.menu.frames.settings.sections.Room;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 *
 * @author indy
 */
public class SettingsFrame extends MenuFrame implements SettingsInterface {

    protected JTabbedPane tabs;
    protected ArrayList<SettingsPanel> sections = new ArrayList<SettingsPanel>();

    public SettingsFrame() {
        super();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        centerWindow();
        setVisible(true);
        initSections();
    }

    @Override
    protected String getSectionName() {
        return "settings";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(600, 400));
        setSize(getPreferredSize());
        setLayout(new BorderLayout());
        tabs = new JTabbedPane();
        add(tabs);
    }

    protected void initSections() {
        addSection(new Connection());
        addSection(new Room());
        addSection(new LoadAndSave(this));
    }

    public void addSection(SettingsPanel panel) {
        tabs.add(panel.getPanelName(), panel);
        sections.add(panel);
    }

    @Override
    public void resetAll() {
        for (SettingsPanel panel : sections) {
            panel.reset();
        }
    }

    @Override
    public void setAll() {
        for (SettingsPanel panel : sections) {
            panel.set();
        }
    }
}
