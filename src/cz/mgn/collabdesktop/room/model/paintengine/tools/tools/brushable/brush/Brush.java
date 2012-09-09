/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable.brush;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author indy
 */
public class Brush implements Cloneable {

    /**
     * brush name
     */
    protected String name = "";
    /**
     * brush source image
     */
    protected BufferedImage sourceImage;
    /**
     * brush scaled image (source after scaling)
     */
    protected BufferedImage scaledImage;
    /**
     * brush paint image (scale image after colorizing and transparenting)
     */
    protected BufferedImage paintImage;
    protected float step = 1f;
    protected float scale = 1f;
    protected float jitter = 0f;
    protected float opacity = 1f;
    /**
     * brush color in ARGB
     */
    protected int color = 0xff000000;

    public Brush(String name, BufferedImage sourceImage) {
        this.name = name;
        init(sourceImage);
    }

    protected void init(BufferedImage sourceImage) {
        sourceImage = correctSourceImage(sourceImage);
        this.sourceImage = sourceImage;
        step = Math.max(1f, (float) Math.max(sourceImage.getWidth(), sourceImage.getHeight()) / 4f);
        generateImages();
    }

    /**
     * repaint source image to ABGR image
     */
    protected BufferedImage correctSourceImage(BufferedImage sourceImage) {
        BufferedImage corrected = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) corrected.getGraphics();
        g.drawImage(sourceImage, 0, 0, null);
        g.dispose();
        return corrected;
    }

    protected void generateImages() {
        int w = (int) (sourceImage.getWidth() * scale);
        w = Math.max(1, w);
        int h = (int) (sourceImage.getHeight() * scale);
        h = Math.max(1, h);

        scaledImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = (Graphics2D) scaledImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(sourceImage, 0, 0, w, h, null);
        g.dispose();

        paintImage = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        g = (Graphics2D) paintImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g.drawImage(scaledImage, 0, 0, null);
        g.dispose();

        for (int x = 0; x < paintImage.getWidth(); x++) {
            for (int y = 0; y < paintImage.getHeight(); y++) {
                int c = paintImage.getAlphaRaster().getPixel(x, y, new int[1])[0];
                c <<= 24;
                c += (color & 0x00ffffff);
                paintImage.setRGB(x, y, c);
            }
        }
    }

    /**
     * returns new instance of same brush, with default properties
     */
    public Brush getInstance() {
        Brush brush = new Brush(getName(), sourceImage);
        return brush;
    }

    /**
     * returns brush name
     */
    public String getName() {
        return name;
    }

    /**
     * get brush source image
     */
    public BufferedImage getSourceImage() {
        return sourceImage;
    }

    /**
     * returns brush image after scaling
     */
    public BufferedImage getScaledImage() {
        return scaledImage;
    }

    public float getStep() {
        return step;
    }

    public void setStep(float step) {
        this.step = step;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        generateImages();
    }

    public float getJitter() {
        return jitter;
    }

    public void setJitter(float jitter) {
        this.jitter = jitter;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        generateImages();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        generateImages();
    }

    public Brush.PaintBrush paintLine(int x1, int y1, int x2, int y2) {
        x1 -= paintImage.getWidth() / 2;
        x2 -= paintImage.getWidth() / 2;
        y1 -= paintImage.getHeight() / 2;
        y2 -= paintImage.getHeight() / 2;


        Brush.PaintBrush paintBrush = new Brush.PaintBrush(paintImage);
        int size = Math.max(paintImage.getWidth(), paintImage.getHeight());

        float dx = x2 - x1;
        float dy = y2 - y1;
        float steps = (float) Math.sqrt(dx * dx + dy * dy) / (step * scale);
        dx /= steps;
        dy /= steps;

        float x = x1;
        float y = y1;
        Random random = null;
        if (jitter != 0) {
            random = new Random();
        }

        for (int i = 0; i <= steps; i++) {
            float xr = x;
            float yr = y;
            if (random != null) {
                float localJitter = jitter * (float) size * random.nextFloat();
                float adx = ((random.nextFloat() * 2f) - 1f) * localJitter;
                float ady = (float) Math.sqrt(localJitter * localJitter - adx * adx);
                if (random.nextBoolean()) {
                    ady *= -1;
                }
                xr += adx;
                yr += ady;
            }
            paintBrush.addPoint((int) xr, (int) yr);
            x += dx;
            y += dy;
        }

        return paintBrush;
    }

    public class PaintBrush {

        protected ArrayList<Point> points = new ArrayList<Point>();
        public BufferedImage paint = null;

        public PaintBrush(BufferedImage paint) {
            this.paint = paint;
        }

        public void addPoint(int x, int y) {
            points.add(new Point(x, y));
        }

        public ArrayList<Point> getPoints() {
            return points;
        }
    }
}
