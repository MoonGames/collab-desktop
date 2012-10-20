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

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.select;

import cz.mgn.collabcanvas.interfaces.selectionable.SelectionUpdate;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class SimpleSelectionUpdate implements SelectionUpdate {

    protected int updateMode;
    protected Point coordinates;
    protected float applyAmount;
    protected BufferedImage updateImage;

    public SimpleSelectionUpdate(int updateMode, Point coordinates, float applyAmount, BufferedImage updateImage) {
        this.updateImage = updateImage;
        this.applyAmount = applyAmount;
        this.coordinates = coordinates;
        this.updateMode = updateMode;
    }

    public SimpleSelectionUpdate(int updateMode, float applyAmount, BufferedImage updateImage) {
        this(updateMode, new Point(0, 0), applyAmount, updateImage);
    }

    @Override
    public int getUpdateMode() {
        return updateMode;
    }

    @Override
    public Point getUpdateCoordinates() {
        return coordinates;
    }

    @Override
    public float getApplyAmount() {
        return applyAmount;
    }

    @Override
    public BufferedImage getUpdateImage() {
        return updateImage;
    }
}
