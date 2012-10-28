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
package cz.mgn.collabdesktop.view.gui.sections;

import cz.mgn.collabdesktop.controller.interfaces.ModelInterface;
import cz.mgn.collabdesktop.view.gui.CollabFrameInterface;
import cz.mgn.collabdesktop.view.interfaces.views.Section;
import cz.mgn.collabdesktop.view.interfaces.views.ServerException;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public abstract class GUISection extends JPanel implements Section {

    protected ModelInterface controller;
    protected CollabFrameInterface frame;

    public void setController(ModelInterface controller) {
        this.controller = controller;
    }

    public void setFrameInterface(CollabFrameInterface frame) {
        this.frame = frame;
    }

    public void dismiss() {
        controller = null;
        frame = null;
    }

    @Override
    public void showServerException(ServerException ex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public abstract Dimension getOuterSize();

    public abstract Dimension getMinimalSize();

    public abstract boolean isResizable();

    public abstract void initComponents();

    @Override
    public abstract String getSectionName();
}
