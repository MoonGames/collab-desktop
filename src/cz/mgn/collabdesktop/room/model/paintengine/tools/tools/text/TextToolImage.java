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

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.text;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class TextToolImage implements ToolImage {

    protected float lastScale = 1f;
    protected BufferedImage source;
    protected BufferedImage toolImage;
    protected BufferedImage toolImageScaled;

    public TextToolImage(BufferedImage source) {
        this.source = source;
        generateImages();
    }

    protected void generateImages() {
        generateScaledImage();
        toolImage = toolImageScaled;
    }

    protected void generateScaledImage() {
        int w = (int) Math.max((lastScale * source.getWidth()), 1);
        int h = (int) Math.max((lastScale * source.getHeight()), 1);
        toolImageScaled = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) toolImageScaled.getGraphics();
        g.drawImage(source, 0, 0, w, h, null);
        g.dispose();
        toolImageScaled = OutlineUtil.generateOutline(toolImageScaled, Color.GRAY, true);
    }

    @Override
    public Point getRelativeLocatoin() {
        return new Point(-toolImage.getWidth() / 2, -toolImage.getHeight() / 2);
    }

    @Override
    public BufferedImage getToolImage() {
        return toolImage;
    }

    @Override
    public boolean isScalingSupported() {
        return true;
    }

    @Override
    public BufferedImage getScaledToolImage(float f) {
        if (lastScale != f) {
            lastScale = f;
            generateScaledImage();
        }
        return toolImageScaled;
    }
}
