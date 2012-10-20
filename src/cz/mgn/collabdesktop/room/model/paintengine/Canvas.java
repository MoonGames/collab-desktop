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

package cz.mgn.collabdesktop.room.model.paintengine;

import cz.mgn.collabcanvas.interfaces.paintable.Paintable;
import cz.mgn.collabcanvas.interfaces.selectionable.Selectionable;
import cz.mgn.collabcanvas.interfaces.visible.Visible;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class Canvas {

    protected Visible visible;
    protected Paintable paintable;
    protected Selectionable selectionable;

    public Canvas(Visible visible, Paintable paintable, Selectionable selectionable) {
        this.visible = visible;
        this.paintable = paintable;
        this.selectionable = selectionable;
    }

    public Visible getVisible() {
        return visible;
    }

    public Paintable getPaintable() {
        return paintable;
    }

    public Selectionable getSelectionable() {
        return selectionable;
    }
}
