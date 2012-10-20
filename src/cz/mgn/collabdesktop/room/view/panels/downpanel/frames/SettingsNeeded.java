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

package cz.mgn.collabdesktop.room.view.panels.downpanel.frames;

import cz.mgn.collabdesktop.room.view.eframes.EFrame;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JTextArea;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class SettingsNeeded extends EFrame {
    
    protected JTextArea message;
    
    public SettingsNeeded(String message) {
        super();
        this.message.setText(message);
    }

    @Override
    protected String getSectionName() {
        return "settings needed";
    }

    @Override
    protected void initComponents() {
        setPreferredSize(new Dimension(300, 60));
        setSize(getPreferredSize());
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        message = new JTextArea();
        message.setBorder(null);
        message.setOpaque(false);
        message.setEditable(false);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        c.add(message);
    }

    @Override
    public void windowClosed() {
    }
}
