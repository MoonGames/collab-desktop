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

package cz.mgn.collabdesktop.utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class ImageUtil {

    protected static Map<String, BufferedImage> resources = new TreeMap<String, BufferedImage>();
    protected static Map<String, BufferedImage> images = new TreeMap<String, BufferedImage>();

    public static BufferedImage loadImageFromResources(String resource) {
        BufferedImage image = resources.get(resource);
        if (image == null) {
            try {
                image = ImageIO.read(ImageUtil.class.getResourceAsStream(resource));
            } catch (IOException ex) {
                Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            resources.put(resource, image);
        }
        return image;
    }

    public static BufferedImage loadImage(String resource) {
        BufferedImage image = images.get(resource);
        if (image == null) {
            try {
                image = ImageIO.read(new File(resource));
            } catch (IOException ex) {
                Logger.getLogger(ImageUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
            images.put(resource, image);
        }
        return image;
    }
}
