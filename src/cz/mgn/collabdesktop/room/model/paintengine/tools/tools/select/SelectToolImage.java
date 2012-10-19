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
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.select;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class SelectToolImage implements ToolImage {

    protected float lastScale = 1f;
    protected Point location;
    protected BufferedImage toolImage;
    protected BufferedImage toolImageScaled;
    protected int type;
    protected int width = 0;
    protected int height = 0;

    public SelectToolImage(int type, int width, int height, boolean invertX, boolean invertY) {
        this.type = type;
        this.width = width;
        this.height = height;
        location = new Point(invertX ? -width : 0, invertY ? -height : 0);
        generate();
    }

    protected void generate() {
        generateScaled();
        toolImage = toolImageScaled;
    }

    protected void generateScaled() {
        int w = (int) Math.max(width * lastScale, 1);
        int h = (int) Math.max(height * lastScale, 1);
        BufferedImage sourceScaled = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) sourceScaled.getGraphics();
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f, new float[]{10f}, 0.0f));
        if (type == SelectPanel.SHAPE_OVAL) {
            g.drawOval(0, 0, w - 1, h - 1);
        } else {
            g.drawRect(0, 0, w - 1, h - 1);
        }
        g.dispose();
        toolImageScaled = OutlineUtil.generateOutline(sourceScaled, Color.BLUE, Color.WHITE, true);
    }

    @Override
    public Point getRelativeLocatoin() {
        return location;
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
            generateScaled();
        }
        return toolImageScaled;
    }
}
