/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel;

import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel.Cache.CacheUpdate;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class Zoom implements Cache.UpdateCacheInterface {

    protected static final int ALPHA_BACKGROUND_BOX_SIZE = 10;
    protected static final int ALPHA_BACKGROUND_COLOR_A = 0xff999999;
    protected static final int ALPHA_BACKGROUND_COLOR_B = 0xff666666;
    //
    protected float zoom = 1f;
    protected int width = 1;
    protected int height = 1;
    protected UpdateRect updateRect1 = new UpdateRect();
    protected UpdateRect updateRect2 = new UpdateRect();
    protected BufferedImage alphaBackground = null;
    protected BufferedImage cacheImage = null;
    protected BufferedImage zoomImage = null;

    public Zoom() {
        synchronized (this) {
            regenerate();
        }
    }

    protected void regenerate() {
        generateAlphaBackground(getZoomedWidth(), getZoomedHeight());
        if (cacheImage == null) {
            cacheImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        }
        if (zoomImage == null || zoomImage.getWidth() != getZoomedWidth() || zoomImage.getHeight() != getZoomedHeight()) {
            zoomImage = new BufferedImage(getZoomedWidth(), getZoomedHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        }
        draw(0, 0, width, height);
        updateRect1.add(0, 0, getZoomedWidth(), getZoomedHeight());
        updateRect2.reset();
    }

    protected void generateAlphaBackground(int width, int height) {
        width = Math.max(1, width);
        height = Math.max(1, height);
        alphaBackground = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        int w = alphaBackground.getWidth();
        int h = alphaBackground.getHeight();
        Graphics2D ga = (Graphics2D) alphaBackground.getGraphics();

        ga.setColor(new Color(ALPHA_BACKGROUND_COLOR_B));
        ga.fillRect(0, 0, w, h);
        ga.setColor(new Color(ALPHA_BACKGROUND_COLOR_A));

        boolean shiftB = true;
        for (int y = 0; y < h; y += ALPHA_BACKGROUND_BOX_SIZE) {
            int shift = shiftB ? ALPHA_BACKGROUND_BOX_SIZE : 0;
            shiftB = !shiftB;
            for (int x = 0; x < w; x += (2 * ALPHA_BACKGROUND_BOX_SIZE)) {
                ga.fillRect(shift + x, y, ALPHA_BACKGROUND_BOX_SIZE, ALPHA_BACKGROUND_BOX_SIZE);
            }
        }
        ga.dispose();
    }

    protected void draw(int x, int y, int width, int height) {
        int dx1 = x;
        int dy1 = y;
        int dx2 = x + width;
        int dy2 = y + height;

        int sx1 = dx1;
        int sy1 = dy1;
        int sx2 = sx1 + (dx2 - dx1);
        int sy2 = sy1 + (dy2 - dy1);

        dx1 *= zoom;
        dy1 *= zoom;
        dx2 *= zoom;
        dy2 *= zoom;

        Graphics2D g = (Graphics2D) zoomImage.getGraphics();
        g.drawImage(alphaBackground, dx1, dy1, dx2, dy2, dx1, dy1, dx2, dy2, null);
        g.drawImage(cacheImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        g.dispose();
    }

    public void setZoom(float zoom) {
        synchronized (this) {
            updateRect1.add(0, 0, getZoomedWidth(), getZoomedHeight());
            updateRect2.reset();
            this.zoom = zoom;
            regenerate();
        }
    }

    public void setResolution(int width, int height) {
        synchronized (this) {
            updateRect1.add(0, 0, getZoomedWidth(), getZoomedHeight());
            updateRect2.reset();
            this.width = width;
            this.height = height;
            regenerate();
        }
    }

    public int getWidth() {
        synchronized (this) {
            return width;
        }
    }

    public int getHeight() {
        synchronized (this) {
            return height;
        }
    }

    public int getZoomedWidth() {
        synchronized (this) {
            return Math.max(1, (int) (width * zoom));
        }
    }

    public int getZoomedHeight() {
        synchronized (this) {
            return Math.max(1, (int) (height * zoom));
        }
    }

    public float getZoom() {
        synchronized (this) {
            return zoom;
        }
    }

    public BufferedImage getZoomImage() {
        synchronized (this) {
            return zoomImage;
        }
    }

    public BufferedImage getCacheImage() {
        synchronized (this) {
            return cacheImage;
        }
    }

    /**
     * get coords of update rects and reset rects
     */
    public int[] getUpdateRects() {
        synchronized (this) {
            int rects[] = null;
            if (updateRect1.getRect() != null && updateRect2.getRect() != null) {
                int[] rect1 = updateRect1.getRect();
                int[] rect2 = updateRect2.getRect();
                rects = new int[]{rect1[0], rect1[1], rect1[2], rect1[3], rect2[0], rect2[1], rect2[2], rect2[3]};
            } else if (updateRect1.getRect() != null) {
                rects = updateRect1.getRect();
            } else if (updateRect2.getRect() != null) {
                rects = updateRect2.getRect();
            }
            if (rects != null) {
                for (int i = 0; i < rects.length; i++) {
                    rects[i] *= zoom;
                }
            }
            updateRect1.reset();
            updateRect2.reset();
            return rects;
        }
    }
    
    public int pickColorInCache(int x, int y) {
        synchronized (cacheImage) {
            return cacheImage.getRGB(x, y);
        }
    }

    @Override
    public void updateCache(CacheUpdate cacheUpdate) {
        synchronized (this) {
            updateRect1.add(cacheUpdate.getUpdateRect1());
            updateRect2.add(cacheUpdate.getUpdateRect2());

            cacheImage = cacheUpdate.getImage();

            if (Math.floor(zoom) == zoom) {
                int rect[] = updateRect1.getRect();
                if (rect != null) {
                    draw(rect[0], rect[1], rect[2], rect[3]);
                }
                rect = updateRect2.getRect();
                if (rect != null) {
                    draw(rect[0], rect[1], rect[2], rect[3]);
                }
            } else {
                draw(0, 0, width, height);
            }
        }
    }
}
