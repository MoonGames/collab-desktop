/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool.tools.brushable.brush;

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
 * @author indy
 */
public class BrushIO {

    protected static final String BRUSHS_DIRECTORY = "brushs";

    public static Brush loadFromResources(String fileName, String name) {
        BufferedImage brushImage = ImageUtil.loadImageFromResources("/resources/brushs/" + fileName + ".png");
        Brush brush = new Brush(brushImage, name);
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
        Brush brush = new Brush(brushImage, name);
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
        ImageIO.write(brush.getBrushImage(), "PNG", brushFile);
    }

    protected static void createDirectory() {
        File directory = new File(HomeStorage.getHomeFolder().getPath() + "/" + BRUSHS_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();;
        }
    }
}
