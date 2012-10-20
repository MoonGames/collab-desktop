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

package cz.mgn.collabdesktop.room.model.executor;

import cz.mgn.collabcanvas.interfaces.networkable.NetworkUpdate;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ExecutorNetworkUpdate implements NetworkUpdate {

    protected int updateID;
    protected int updateLayerID;
    protected int updateCanvasID = 0;
    protected boolean isAdd;
    protected Point updateCoordinates;
    protected BufferedImage updateImage;

    public ExecutorNetworkUpdate(int updateID, int updateLayerID, boolean isAdd, Point updateCoordinates, BufferedImage updateImage) {
        this.updateID = updateID;
        this.updateLayerID = updateLayerID;
        this.isAdd = isAdd;
        this.updateCoordinates = updateCoordinates;
        this.updateImage = updateImage;
    }

    @Override
    public int getUpdateID() {
        return updateID;
    }

    @Override
    public int getUpdateLayerID() {
        return updateLayerID;
    }

    @Override
    public int getUpdateCanvasID() {
        return updateCanvasID;
    }

    @Override
    public boolean isAdd() {
        return isAdd;
    }

    @Override
    public Point getUpdateCoordinates() {
        return updateCoordinates;
    }

    @Override
    public BufferedImage getUpdateImage() {
        return updateImage;
    }
}
