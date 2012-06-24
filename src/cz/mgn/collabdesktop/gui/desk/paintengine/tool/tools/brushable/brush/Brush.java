/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.brushable.brush;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 *    @author indy
 */
public class Brush {

    protected BrushListener brushListener = null;
    //
    protected String name = "";
    protected BufferedImage brushCursor = null;
    protected BufferedImage brushImage = null;
    protected BufferedImage paintImageColor = null;
    protected BufferedImage paintImageNoColor = null;
    protected float step = 1f;
    protected Color color = Color.BLACK;
    protected float scale = 1f;
    protected float jitter = 0f;
    protected float opacity = 1f;

    public Brush(BufferedImage brushImage, String name) {
        this.name = name;
        if (brushImage.getType() != BufferedImage.TYPE_BYTE_GRAY) {
            this.brushImage = new BufferedImage(brushImage.getWidth(), brushImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D g = (Graphics2D) this.brushImage.getGraphics();
            g.drawImage(brushImage, 0, 0, null);
            g.dispose();
        } else {
            this.brushImage = brushImage;
        }
        step = Math.max(1f, (float) Math.max(brushImage.getWidth(), brushImage.getHeight()) / 4f);
        init();
    }

    public void setBrushListener(BrushListener brushListener) {
        this.brushListener = brushListener;
    }

    public Brush cloneBrush() {
        BufferedImage cloneBrushImage = new BufferedImage(brushImage.getWidth(), brushImage.getHeight(), brushImage.getType());
        brushImage.copyData(cloneBrushImage.getRaster());
        return new Brush(cloneBrushImage, name);
    }

    public String getName() {
        return name;
    }

    protected void init() {
        generate();
    }

    protected void generate() {
        generatePaintImage();
        generateBrushCursor();
    }

    protected void generateBrushCursor() {
        brushCursor = OutlineUtil.generateOutline(paintImageNoColor, Color.BLACK, false);
    }

    protected void generatePaintImage() {
        int w = (int) (brushImage.getWidth() * scale);
        w = Math.max(1, w);
        int h = (int) (brushImage.getHeight() * scale);
        h = Math.max(1, h);
        //no color generating
        paintImageNoColor = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = (Graphics2D) paintImageNoColor.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.drawImage(brushImage, 0, 0, w, h, null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f - opacity));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
        g.dispose();

        //color generating
        paintImageColor = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        paintAddBrush(paintImageColor, paintImageNoColor, 0, 0, color.getRGB());
    }

    public float getScale() {
        return scale;
    }

    public float getJitter() {
        return jitter;
    }

    public float getStep() {
        return step;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setColor(int color) {
        this.color = new Color(color);
        generate();
    }

    public void setScale(float scale) {
        this.scale = scale;
        generate();
        if (brushListener != null) {
            brushListener.brushScaled(scale);
        }
    }

    public void setStep(float step) {
        this.step = step;
        if (brushListener != null) {
            brushListener.brushStep(step);
        }
    }

    public void setJitter(float jitter) {
        this.jitter = jitter;
        if (brushListener != null) {
            brushListener.brusheJitter(jitter);
        }
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
        generate();
    }

    public BufferedImage getCuror() {
        return brushCursor;
    }

    public BufferedImage getBrushImage() {
        return brushImage;
    }

    public PaintBrush paintLine(int x1, int y1, int x2, int y2, boolean colored) {
        x1 -= paintImageColor.getWidth() / 2;
        x2 -= paintImageColor.getWidth() / 2;
        y1 -= paintImageColor.getHeight() / 2;
        y2 -= paintImageColor.getHeight() / 2;


        PaintBrush paintBrush = null;
        int size = Math.max(paintImageColor.getWidth(), paintImageColor.getHeight());
        if (colored) {
            paintBrush = new PaintBrush(paintImageColor);
        } else {
            paintBrush = new PaintBrush(paintImageNoColor);
        }

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
