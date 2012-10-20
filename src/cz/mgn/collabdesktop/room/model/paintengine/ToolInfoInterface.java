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

import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public interface ToolInfoInterface {

    /**
     * returns standart tool icon
     */
    public BufferedImage getToolIcon();

    /**
     * returns tool icon with selected size
     *
     * if icon in this size doesnt exist returns scaled icon
     */
    public BufferedImage getToolIcon(int width, int height);

    /**
     * returns tool name
     */
    public String getToolName();

    /**
     * returns tool description
     */
    public String getToolDescription();

    /**
     * returns tool panel (with tool properties)
     */
    public JPanel getToolPanel();
}
