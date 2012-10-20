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

package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush;

import cz.mgn.collabdesktop.utils.ImageUtil;
import cz.mgn.collabdesktop.utils.homestorage.HomeStorage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Martin Indra <aktive@seznam.cz>
 */
public class BrushIO {

    protected static final String BRUSHS_DIRECTORY = "brushs";

    public static Brush loadFromResources(String fileName, String name) {
        BufferedImage brushImage = ImageUtil.loadImageFromResources("/resources/brushs/" + fileName + ".png");
        Brush brush = new Brush(name, brushImage);
        return brush;
    }

    public static ArrayList<Brush> loadFromUser() throws IOException {
        createDirectory();
        ArrayList<Brush> brushs = new ArrayList<Brush>();
        File directory = new File(HomeStorage.getHomeFolder().getPath() + "/" + BRUSHS_DIRECTORY);
        String[] files = directory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (name.endsWith(".png") || name.endsWith(".PNG"));
            }
        });
        for (int i = 0; i < files.length; i++) {
            //FIXME: set names beter
            brushs.add(loadFromUser(directory.getPath() + "/" + files[i], files[i]));
        }
        return brushs;
    }

    protected static Brush loadFromUser(String path, String name) {
        BufferedImage brushImage = ImageUtil.loadImage(path);
        Brush brush = new Brush(name, brushImage);
        return brush;
    }

    public static void saveBrush(Brush brush) throws IOException {
        createDirectory();
        File directory = new File(HomeStorage.getHomeFolder().getPath() + "/" + BRUSHS_DIRECTORY);
        String[] files = directory.list();
        int index = 0;
        boolean nameDone = false;
        String name = "";
        while (!nameDone) {
            name = "brush-" + index + ".png";
            boolean rename = false;
            for (int i = 0; i < files.length && !rename; i++) {
                if (name.equals(files[i])) {
                    rename = true;
                }
            }
            if (rename) {
                index++;
            } else {
                nameDone = true;
            }
        }
        File brushFile = new File(HomeStorage.getHomeFolder().getPath() + "/" + BRUSHS_DIRECTORY + "/" + name);
        ImageIO.write(brush.getSourceImage(), "PNG", brushFile);
    }

    protected static void createDirectory() {
        File directory = new File(HomeStorage.getHomeFolder().getPath() + "/" + BRUSHS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();;
        }
    }
}
