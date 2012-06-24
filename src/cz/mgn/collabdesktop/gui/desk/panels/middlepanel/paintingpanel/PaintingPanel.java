/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel;

import cz.mgn.collabdesktop.gui.desk.DeskInterface;
import cz.mgn.collabdesktop.gui.desk.executor.CommandExecutor;
import cz.mgn.collabdesktop.gui.desk.executor.Paintable;
import cz.mgn.collabdesktop.gui.desk.panels.downpanel.interfaces.PaintingSystemInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.PaintingInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.infopanel.InfoInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.Painting;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.CursorInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.Tool;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.Tool.ToolImage;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintingpanel.Cache.CacheUpdate;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *          @author indy
 */
public class PaintingPanel extends JPanel implements Paintable, MouseMotionListener, MouseListener, MouseWheelListener, KeyListener, ComponentListener, CursorInterface, PaintingInterface, PaintingSystemInterface, Cache.UpdateCacheInterface {

    protected static final int DEFAULT_BACKGROUND = 0xfff6f6f6;
    //
    protected DeskInterface desk = null;
    protected InfoInterface info = null;
    protected Painting painting = null;
    protected Cache cache = null;
    protected Controller controller = null;
    protected UpdateRect updateRectTool = new UpdateRect();
    protected UpdateRect updateRectStatic = new UpdateRect();
    protected volatile boolean repaintAll = false;
    protected volatile boolean repaintStatic = false;
    protected Point cursor = null;
    protected BufferedImage defaultCursorImage = null;
    protected BufferedImage brushCursorSource = null;
    protected BufferedImage brushCursor = null;
    //protected float zoom = 1f;
    protected Zoom zoom = new Zoom();
    protected int shiftX = 0;
    protected int shiftY = 0;

    public PaintingPanel(CommandExecutor executor, DeskInterface desk) {
        this.desk = desk;
        init(executor);
    }

    public void setInfoInterface(InfoInterface info) {
        this.info = info;
    }

    protected void init(CommandExecutor executor) {
        defaultCursorImage = ImageUtil.loadImageFromResources("/resources/images/cursors/default.gif");

        cache = new Cache(this);
        painting = new Painting(executor, this, cache);
        cache.init(painting);
        controller = new Controller(painting);
        executor.setPaintable(this);

        addMouseListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        addMouseListener(controller);
        addMouseMotionListener(controller);

        setResolution(1, 1);
        setZoom(1f);
    }

    protected int[] rectToVisible(int[] rect) {
        int x = rect[0];
        int y = rect[1];
        int x2 = rect[2];
        int y2 = rect[3];
        Rectangle visible = getVisibleRect();
        x2 = Math.min(x + x2, visible.x + visible.width);
        y2 = Math.min(y + y2, visible.y + visible.height);
        x = Math.max(x, visible.x);
        y = Math.max(y, visible.y);
        return new int[]{x, y, Math.max(0, x2 - x), Math.max(0, y2 - y)};
    }

    @Override
    public void paintComponent(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        Rectangle visibleRect = getVisibleRect();
        if (repaintAll) {
            repaintAll = false;
            updateRectStatic.reset();
            paintBackgroundAndCache(g, visibleRect.x, visibleRect.y, visibleRect.width, visibleRect.height);
        } else {
            boolean any = false;
            int[] zoomRects = zoom.getUpdateRects();
            if (zoomRects != null) {
                for (int i = 0; (i + 4) <= zoomRects.length; i += 4) {
                    any = true;
                    int zRect[] = new int[4];
                    System.arraycopy(zoomRects, i, zRect, 0, 4);
                    zRect = shiftRect(zRect);
                    zRect = rectToVisible(zRect);
                    paintCache(g, zRect[0], zRect[1], zRect[2], zRect[3]);
                }
            }

            int[] staticRect = updateRectStatic.getRect();
            updateRectStatic.reset();
            if (staticRect != null) {
                if (repaintStatic) {
                    repaintStatic = false;
                    any = true;
                }
                staticRect = rectToVisible(staticRect);
                paintBackgroundAndCache(g, staticRect[0], staticRect[1], staticRect[2], staticRect[3]);
            }
            int[] toolRect = updateRectTool.getRect();
            updateRectTool.reset();
            if (toolRect != null) {
                toolRect = rectToVisible(toolRect);
                paintBackgroundAndCache(g, toolRect[0], toolRect[1], toolRect[2], toolRect[3]);
            }
            if (staticRect != null) {
                if (!any) {
                    paintBackgroundAndCache(g, visibleRect.x, visibleRect.y, visibleRect.width, visibleRect.height);
                }
            }
            paintTool(g);
        }

    }

    protected void paintBackgroundAndCache(Graphics2D g, int x, int y, int width, int height) {
        paintBackground(g, x, y, width, height);
        paintCache(g, x, y, width, height);
    }

    protected int[] shiftRect(int[] rect) {
        rect[0] += shiftX;
        rect[1] += shiftY;
        return rect;
    }

    protected void paintCache(Graphics2D g, int x, int y, int width, int height) {
        int w = zoom.getZoomedWidth();
        int h = zoom.getZoomedHeight();
        paintCacheBorder(g, shiftX, shiftY, w, h, x, y, width, height);

        int dx1 = Math.max(x, shiftX);
        int dy1 = Math.max(y, shiftY);
        int dx2 = Math.min(x + width, shiftX + w);
        int dy2 = Math.min(y + height, shiftY + h);
        if ((dx2 - dx1) > 0 && (dy2 - dy1) > 0) {
            int sx1 = dx1 - shiftX;
            int sy1 = dy1 - shiftY;
            int sx2 = sx1 + (dx2 - dx1);
            int sy2 = sy1 + (dy2 - dy1);
            g.drawImage(zoom.getZoomImage(), dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
    }

    protected void paintCacheBorder(Graphics2D g, int x, int y, int width, int height, int uX, int uY, int uWidth, int uHeight) {
        x -= 1;
        y -= 1;
        width += 1;
        height += 1;
        g.setColor(Color.BLACK);
        Stroke before = g.getStroke();

        //TODO: paint only update (rect ux, uy, uwidth, uheight)
        g.setStroke(new BasicStroke(1f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{2f}, 0f));
        g.drawRect(x, y, width, height);

        g.setStroke(before);
    }

    protected void paintBackground(Graphics2D g, int x, int y, int width, int height) {
        g.setColor(new Color(DEFAULT_BACKGROUND));
        g.fillRect(x, y, width, height);
    }

    protected void paintTool(Graphics2D g) {
        ToolImage ti = painting.getToolImage();
        if (ti != null) {
            int x = (int) (ti.getX() * zoom.getZoom()) + shiftX;
            int y = (int) (ti.getY() * zoom.getZoom()) + shiftY;
            g.drawImage(ti.getImage(), x, y, null);
            updateRectTool.add(x, y, ti.getImage().getWidth(), ti.getImage().getHeight());
        }
        if (cursor != null && brushCursor != null) {
            int xx = cursor.x - (brushCursor.getWidth() / 2);
            int yy = cursor.y - (brushCursor.getHeight() / 2);
            updateRectStatic.add(xx, yy, brushCursor.getWidth(), brushCursor.getHeight());
            g.drawImage(brushCursor, xx, yy, null);
        }
    }

    protected void generateBrushCursorImage() {
        if (brushCursorSource != null) {
            int w = brushCursorSource.getWidth();
            int h = brushCursorSource.getHeight();
            int w2 = Math.max(1, (int) (w * zoom.getZoom()));
            int h2 = Math.max(1, (int) (h * zoom.getZoom()));
            brushCursor = new BufferedImage(w2, h2, BufferedImage.TYPE_4BYTE_ABGR);
            for (int x = 0; x < w; x++) {
                for (int y = 0; y < h; y++) {
                    int pixel = brushCursorSource.getRGB(x, y);
                    if ((pixel & 0xff000000) != 0x00000000) {
                        int xl = Math.min(w2 - 1, (int) (x * zoom.getZoom()));
                        int yl = Math.min(h2 - 1, (int) (y * zoom.getZoom()));
                        brushCursor.setRGB(xl, yl, pixel);
                    }
                }
            }
        } else {
            brushCursor = null;
        }
    }

    protected void recountShift() {
        int w = zoom.getZoomedWidth();
        int h = zoom.getZoomedHeight();
        shiftX = (getWidth() - w) / 2;
        shiftY = (getHeight() - h) / 2;
        controller.setShift(shiftX, shiftY);
    }

    protected void setZoom(float zoom) {
        repaintAll = true;
        zoom = Math.max(0.2f, Math.min(5f, zoom));
        if (info != null) {
            info.setZoom(zoom);
        }
        controller.setZoom(zoom);
        this.zoom.setZoom(zoom);
        generateBrushCursorImage();
        setPreferredSize(new Dimension(this.zoom.getZoomedWidth(), this.zoom.getZoomedHeight()));
        updateUI();
        recountShift();
        repaint();
    }

    @Override
    public void setToolCursorIcon(BufferedImage toolCursorIcon, String name) {
        synchronized (this) {
            BufferedImage cursorImage = new BufferedImage(defaultCursorImage.getWidth() + 1 + toolCursorIcon.getWidth(),
                    defaultCursorImage.getHeight() + 1 + toolCursorIcon.getHeight(), BufferedImage.TYPE_INT_ARGB);

            Graphics2D g = (Graphics2D) cursorImage.getGraphics();
            g.drawImage(defaultCursorImage, 0, 0, null);
            int x = cursorImage.getWidth() - toolCursorIcon.getWidth();
            int y = cursorImage.getHeight() - toolCursorIcon.getHeight();
            g.drawImage(toolCursorIcon, x, y, null);
            g.dispose();

            //FIXME: kresli se blbe

            setCursor(getToolkit().createCustomCursor(cursorImage, new Point(0, 0), name));
        }
    }

    public void dismiss() {
        synchronized (this) {
            painting.dismiss();
        }
    }

    @Override
    public void setColor(int color) {
        synchronized (this) {
            painting.setColor(color);
        }
    }

    @Override
    public void setTool(Tool tool) {
        synchronized (this) {
            if (info != null) {
                info.showInfoString(tool.getToolName() + ": " + tool.getToolDescription());
            }
            painting.setTool(tool);
        }
    }

    @Override
    public void setPaintingLayer(int layer) {
        synchronized (this) {
            cache.setPaintingLayer(layer);
        }
    }

    @Override
    public BufferedImage getSavableImage() {
        synchronized (this) {
            return zoom.getCacheImage();
        }
    }

    @Override
    public float zoomTo100() {
        synchronized (this) {
            setZoom(1f);
            return zoom.getZoom();
        }
    }

    @Override
    public float zoom(float about) {
        synchronized (this) {
            setZoom(this.zoom.getZoom() + about);
            return this.zoom.getZoom();
        }
    }

    @Override
    public void setBrushCursor(BufferedImage brushCursor) {
        synchronized (this) {
            this.brushCursorSource = brushCursor;
            generateBrushCursorImage();
            repaintStatic = true;
            repaint();
        }
    }

    @Override
    public void paint(BufferedImage change, int identificator, int layerID, int x, int y) {
        synchronized (this) {
            cache.paint(change, identificator, layerID, x, y);
        }
    }

    @Override
    public void remove(BufferedImage change, int identificator, int layerID, int x, int y) {
        synchronized (this) {
            cache.remove(change, identificator, layerID, x, y);
        }
    }

    @Override
    public void setLayersOrder(int[] layersOrderIDs) {
        synchronized (this) {
            cache.setLayersOrder(layersOrderIDs);
        }
    }

    @Override
    public void setResolution(int width, int height) {
        synchronized (this) {
            desk.resolutionInfo(width, height);
            repaintAll = true;
            this.zoom.setResolution(width, height);
            cache.setResolution(width, height);
            setPreferredSize(new Dimension(zoom.getZoomedWidth(), zoom.getZoomedHeight()));
            updateUI();
            recountShift();
            repaint();
        }
    }

    protected void infoMouseCoords(MouseEvent e) {
        if (info != null) {
            float x = e.getX() - shiftX;
            float y = e.getY() - shiftY;
            if (x < 0 || y < 0 || x >= zoom.getZoomedWidth() || y >= zoom.getZoomedHeight()) {
                info.setMouseCoord(-1, -1);
            } else {
                x /= zoom.getZoom();
                y /= zoom.getZoom();
                info.setMouseCoord(x, y);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        synchronized (this) {
            infoMouseCoords(e);
            cursor = e.getPoint();
            repaintStatic = true;
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        synchronized (this) {
            infoMouseCoords(e);
            cursor = e.getPoint();
            repaintStatic = true;
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        synchronized (this) {
            cursor = e.getPoint();
            repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        synchronized (this) {
            if (info != null) {
                info.setMouseCoord(-1, -1);
            }
            cursor = null;
            repaint();
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        synchronized (this) {
            repaintAll = true;
            recountShift();
            repaint();
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
        synchronized (this) {
            repaintAll = true;
            repaint();
        }
    }

    @Override
    public void componentShown(ComponentEvent e) {
        synchronized (this) {
            repaintAll = true;
            repaint();
        }
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void updateCache(CacheUpdate cacheUpdate) {
        zoom.updateCache(cacheUpdate);
        repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        synchronized (this) {
            painting.mouseWheeled(e.getWheelRotation(), e.isShiftDown(), e.isControlDown());
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        synchronized (this) {
            painting.keyPressed(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        synchronized (this) {
            painting.keyReleased(e.getKeyCode());
        }
    }

    @Override
    public int pickColor(int x, int y) {
        synchronized (this) {
            if (x >= 0 && y >= 0 && x < zoom.getWidth() && y < zoom.getHeight()) {
                Layer layer = cache.getLayerByID(painting.getLayerID());
                int color = -1;
                if (layer != null) {
                    BufferedImage pick = painting.addLocalChanges(layer.getImage(), x, y, 1, 1);
                    color = pick.getRGB(x, y);
                } else {
                    color = zoom.pickColorInCache(x, y);
                }
                int alpha = (color & 0xff000000) >>> 24;
                if (alpha != 0) {
                    return color & 0x00ffffff;
                }
            }
            return -1;
        }
    }

    @Override
    public int getPaintingWidth() {
        synchronized (this) {
            return zoom.getWidth();
        }
    }

    @Override
    public int getPaintingHeight() {
        synchronized (this) {
            return zoom.getHeight();
        }
    }

    @Override
    public BufferedImage getPaintingImage() {
        synchronized (this) {
            Layer layer = cache.getLayerByID(painting.getLayerID());
            if (layer != null) {
                return painting.addLocalChanges(layer.getImage(), 0, 0, zoom.getWidth(), zoom.getHeight());
            }
            return zoom.getCacheImage();
        }
    }
}
