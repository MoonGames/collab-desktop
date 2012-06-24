/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 *     @author indy
 */
public class ImageProcessor {

    public static void paintAddBrush(BufferedImage image, BufferedImage brush, int x, int y, int color) {
        int x1 = x;
        int y1 = y;
        int x2 = Math.min(image.getWidth(), x1 + brush.getWidth());
        int y2 = Math.min(image.getHeight(), y1 + brush.getHeight());

        for (int xx = x1; xx < x2; xx++) {
            for (int yy = y1; yy < y2; yy++) {
                int gray = brush.getRGB(xx - x, yy - y);
                gray &= 0x000000ff;
                if (gray != 255) {
                    int pixel = color;
                    gray = (int) ((((pixel & 0xff000000) >>> 24) * (255 - gray)) / 255f);
                    pixel &= 0x00ffffff;
                    pixel += (gray << 24);
                    image.setRGB(xx, yy, pixel);
                }
            }
        }
    }

    public static void paintAdd(BufferedImage image, BufferedImage add, int x, int y) {
        Graphics2D g = (Graphics2D) image.getGraphics();
        g.drawImage(add, x, y, null);
        g.dispose();
    }

    public static void paintRemove(BufferedImage image, BufferedImage remove, int x, int y) {
        int x2 = Math.min(remove.getWidth() + x, image.getWidth());
        int y2 = Math.min(remove.getHeight() + y, image.getHeight());
        int xs = Math.max(x, 0);
        int ys = Math.max(y, 0);

        for (int xx = xs; xx < x2; xx++) {
            for (int yy = ys; yy < y2; yy++) {
                int gray1 = image.getRGB(xx, yy);
                gray1 &= 0x000000ff;
                int gray2 = remove.getRGB(xx - x, yy - y);
                gray2 &= 0x000000ff;
                int c = (gray1 * gray2) / 255;
                int color = 0xff000000 + c + (c << 8) + (c << 16);
                image.setRGB(xx, yy, color);
            }
        }
    }

    public static void addToImage(BufferedImage image, BufferedImage add, int x, int y, int uX, int uY, int uWidth, int uHeight) {
        int dx1 = Math.max(x, uX);
        int dy1 = Math.max(y, uY);
        int dx2 = Math.min(x + add.getWidth(), uX + uWidth);
        int dy2 = Math.min(y + add.getHeight(), uY + uHeight);
        if ((dx2 - dx1) > 0 && (dy2 - dy1) > 0) {
            int sx1 = dx1 - x;
            int sy1 = dy1 - y;
            int sx2 = sx1 + (dx2 - dx1);
            int sy2 = sy1 + (dy2 - dy1);
            Graphics2D g = (Graphics2D) image.getGraphics();
            g.drawImage(add, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
            g.dispose();
        }
    }

    public static void removeFromImage(BufferedImage image, BufferedImage remove, int x, int y, int uX, int uY, int uWidth, int uHeight) {
        int dx1 = Math.max(x, uX);
        dx1 = Math.max(dx1, 0);
        int dy1 = Math.max(y, uY);
        dy1 = Math.max(dy1, 0);

        int dx2 = Math.min(x + remove.getWidth(), uX + uWidth);
        dx2 = Math.min(dx2, image.getWidth());
        int dy2 = Math.min(y + remove.getHeight(), uY + uHeight);
        dy2 = Math.min(dy2, image.getHeight());

        for (int xx = dx1; xx < dx2; xx++) {
            for (int yy = dy1; yy < dy2; yy++) {
                int gray2 = remove.getRGB(xx - x, yy - y);
                gray2 &= 0x000000ff;
                if (gray2 != 255) {
                    int pixel = image.getRGB(xx, yy);
                    int gray1 = (pixel & 0xff000000) >>> 24;
                    pixel &= 0x00ffffff;
                    pixel += ((int) ((gray1 * gray2) / 255f) << 24);
                    image.setRGB(xx, yy, pixel);
                }
            }
        }
    }
}
