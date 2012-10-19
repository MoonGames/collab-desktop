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
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class ToolsUtils {

    public static BufferedImage transformToolIcon(BufferedImage source, int width, int height) {
        BufferedImage result = source;
        int rw = result.getWidth();
        int rh = result.getHeight();
        if (rw != width || rh != height) {
            float scale = Math.min((float) (width / rw), (float) (height / rh));
            int nw = (int) (rw * scale);
            int nh = (int) (rh * scale);
            int x = 0, y = 0;
            if (nw < width) {
                x = (width - nw) / 2;
            }
            if (nh < height) {
                y = (height - nh) / 2;
            }
            result = new BufferedImage(width, height, source.getType());
            Graphics2D g = (Graphics2D) result.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(source, x, y, nw, nh, null);
            g.dispose();
        }
        return result;
    }
}
