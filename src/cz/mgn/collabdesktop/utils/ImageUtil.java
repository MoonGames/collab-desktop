/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 * @author indy
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
