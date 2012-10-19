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
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import cz.mgn.collabcanvas.interfaces.visible.ToolCursor;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush.Brush;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class BrushToolCursor implements ToolCursor {

    protected Brush brush;
    protected BufferedImage brushCursorOriginal;
    protected BufferedImage brushCursorScaled;
    protected float lastScale = 1f;

    public BrushToolCursor(Brush brush) {
        this.brush = brush;
        generateBrushCursor();
    }

    protected void generateBrushCursor() {
        brushCursorOriginal = OutlineUtil.generateOutline(brush.getScaledImage(), Color.BLACK, false);
        generateBrushCursorScaled(lastScale);
    }

    protected void generateBrushCursorScaled(float scale) {
        BufferedImage outlineSource = brush.getScaledImage();
        int w = (int) Math.max(scale * outlineSource.getWidth(), 1);
        int h = (int) Math.max(scale * outlineSource.getHeight(), 1);
        BufferedImage outlineSourceScaled = new BufferedImage(w, h, outlineSource.getType());

        Graphics2D g = (Graphics2D) outlineSourceScaled.getGraphics();
        g.drawImage(outlineSource, 0, 0, w, h, null);
        g.dispose();
        lastScale = scale;
        brushCursorScaled = OutlineUtil.generateOutline(outlineSourceScaled, Color.BLACK, true);
    }

    @Override
    public Point getRelativeLocatoin() {
        return new Point();
    }

    @Override
    public int getLocationMode() {
        return ToolCursor.LOCATION_MODE_CENTER;
    }

    @Override
    public BufferedImage getCursorImage() {
        return brushCursorOriginal;
    }

    @Override
    public boolean isScalingSupported() {
        return true;
    }

    @Override
    public BufferedImage getScaledCursorImage(float scale) {
        if (scale != lastScale) {
            generateBrushCursorScaled(scale);
        }
        return brushCursorScaled;
    }
}
