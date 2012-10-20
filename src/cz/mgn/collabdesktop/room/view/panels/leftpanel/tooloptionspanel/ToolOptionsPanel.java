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

package cz.mgn.collabdesktop.room.view.panels.leftpanel.tooloptionspanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 *  @author Martin Indra <aktive@seznam.cz>
 */
public class ToolOptionsPanel extends JPanel {

    protected JLabel noOptions = new JLabel("This tool has no options.");

    public ToolOptionsPanel() {
        initComponents();
    }

    protected void initComponents() {
        setBorder(new TitledBorder("Tool options"));
        setPreferredSize(new Dimension(100, 100));
        setLayout(new BorderLayout());
        noOptions.setFont(noOptions.getFont().deriveFont(Font.PLAIN));
        noOptions.setHorizontalAlignment(JLabel.CENTER);
    }

    public void setTollOptionsPanel(JPanel panel) {
        removeAll();
        if (panel != null) {
            add(panel, BorderLayout.CENTER);
        } else {
            add(noOptions, BorderLayout.CENTER);
        }
        updateUI();
    }
}