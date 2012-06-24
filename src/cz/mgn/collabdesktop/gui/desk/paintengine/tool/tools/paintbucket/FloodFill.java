/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.paintbucket;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 *                    @author indy
 */
public class FloodFill {

    protected static int ALPHA = 255;
    //
    protected int[] fill;
    protected int[] background;
    protected float tolerance = 0f;
    protected int width = 0;
    protected int height = 0;
    protected BufferedImage destImage = null;
    protected BufferedImage sourceImage = null;

    public FloodFill(BufferedImage sourceImage, float tolerance) {
        this.width = sourceImage.getWidth();
        this.height = sourceImage.getHeight();
        this.tolerance = tolerance;

        this.sourceImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = this.sourceImage.getGraphics();
        g.drawImage(sourceImage, 0, 0, null);
        g.dispose();
        destImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

    }

    public BufferedImage fill(int x, int y, int color) {
        Color col = new Color(color);
        fill = new int[]{col.getRed(), col.getGreen(), col.getBlue(), ALPHA};
        background = sourceImage.getRaster().getPixel(x, y, new int[4]);
        fillC(x, y);
        return destImage;
    }

    protected void fillC(int x, int y) {
        if (!testPoint(x, y)) {
            return;
        }
        Queue<Point> q = new LinkedList<Point>();
        q.add(new Point(x, y));

        Point p;
        while ((p = q.poll()) != null) {
            if (testPoint(p.x, p.y)) {
                int[] sourceColor = sourceImage.getRaster().getPixel(p.x, p.y, new int[4]);
                int[] destColor = destImage.getRaster().getPixel(p.x, p.y, new int[4]);
                if (!compareAll(fill, destColor) && testFill(sourceColor)) {
                    destImage.getRaster().setPixel(p.x, p.y, fill);

                    q.add(new Point(p.x - 1, p.y));
                    q.add(new Point(p.x + 1, p.y));
                    q.add(new Point(p.x, p.y - 1));
                    q.add(new Point(p.x, p.y + 1));
                }
            }
        }
    }

    protected int[] getPixel(int x, int y, int[] data) {
        int index = (y * width + x) * 4;
        return new int[]{data[index + 3], data[index + 2], data[index + 1], data[index]};
    }

    protected boolean testPoint(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    protected boolean compareAll(int[] a, int[] b) {
        return a[0] == b[0] && a[1] == b[1] && a[2] == b[2] && a[3] == b[3];
    }

    protected float countDistance(int[] a, int[] b) {
        return (float) Math.sqrt(
                Math.pow(a[0] - b[0], 2)
                + Math.pow(a[1] - b[1], 2)
                + Math.pow(a[2] - b[2], 2)
                + Math.pow(a[3] - b[3], 2));
    }

    protected boolean testFill(int[] color) {
        return countDistance(color, background) <= tolerance;
    }
}
