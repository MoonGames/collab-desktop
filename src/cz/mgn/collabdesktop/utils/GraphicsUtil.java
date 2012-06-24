/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 *     @author indy
 */
public class GraphicsUtil {

    public static BufferedImage generateOutline(BufferedImage source, Color color, boolean alpha) {
        int w = source.getWidth();
        int h = source.getHeight();
        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);

        int borderColor = color.getRGB();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                if (!testNeighbour(source, x, y, alpha)) {
                    if (testNeighbours(source, x, y, alpha)) {
                        result.setRGB(x, y, borderColor);
                    }
                }
            }
        }
        return result;
    }

    protected static boolean testNeighbours(BufferedImage source, int x, int y, boolean alpha) {
        boolean up = testNeighbour(source, x, y - 1, alpha);
        boolean down = testNeighbour(source, x, y + 1, alpha);
        boolean left = testNeighbour(source, x - 1, y, alpha);
        boolean right = testNeighbour(source, x + 1, y, alpha);
        return up || down || left || right;
    }

    protected static boolean testNeighbour(BufferedImage source, int x, int y, boolean alpha) {
        if (x >= 0 && y >= 0 && x < source.getWidth() && y < source.getHeight()) {
            int gray = source.getRGB(x, y) & 0x000000ff;
            int alphaI = (source.getRGB(x, y) & 0xff000000) >>> 24;
            return (alpha && alphaI == 0) || (!alpha && gray == 255);
        }
        return true;
    }
}
