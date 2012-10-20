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

package cz.mgn.collabdesktop.room.model.paintengine.tools.paintdata;

import cz.mgn.collabcanvas.interfaces.paintable.PaintData;
import cz.mgn.collabcanvas.interfaces.paintable.PaintImage;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class SingleImagePaintData implements PaintData, PaintImage {

    protected ArrayList<PaintImage> paintImages = new ArrayList<PaintImage>();
    protected ArrayList<Point> paintImagePoints = new ArrayList<Point>();
    protected BufferedImage paintImageImage;
    protected boolean paintImageIsAdditional;

    public SingleImagePaintData(Point paintImagePoint, BufferedImage paintImageImage, boolean paintImageIsAdditional) {
        paintImages.add(this);
        paintImagePoints.add(paintImagePoint);
        this.paintImageImage = paintImageImage;
        this.paintImageIsAdditional = paintImageIsAdditional;
    }

    public SingleImagePaintData(BufferedImage paintImageImage, boolean paintImageIsAdditional) {
        this(new Point(0, 0), paintImageImage, paintImageIsAdditional);
    }

    @Override
    public ArrayList<PaintImage> getPaintImages() {
        return paintImages;
    }

    @Override
    public ArrayList<Point> getApplyPoints() {
        return paintImagePoints;
    }

    @Override
    public BufferedImage getImage() {
        return paintImageImage;
    }

    @Override
    public boolean isAddChange() {
        return paintImageIsAdditional;
    }
}
