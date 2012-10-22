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

package cz.mgn.collabdesktop.menu.frames.settings;

import cz.mgn.collabdesktop.utils.gui.FormUtility;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public abstract class SettingsPanel extends JPanel {

    protected JPanel form;
    protected FormUtility formUtility;

    public SettingsPanel() {
        setMinimumSize(new Dimension(400, 400));
        form = new JPanel();
        setLayout(new BorderLayout());
        add(form, BorderLayout.NORTH);
        form.setLayout(new GridBagLayout());
        formUtility = new FormUtility();
    }

    public abstract void reset();

    public abstract void set();

    public abstract String getPanelName();
    
    /** returns if user change values and not set it */
    public abstract boolean isChanged();
}
