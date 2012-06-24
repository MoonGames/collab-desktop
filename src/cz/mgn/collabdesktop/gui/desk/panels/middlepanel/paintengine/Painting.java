/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine;

import cz.mgn.collabdesktop.gui.desk.executor.CommandExecutor;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.PaintingInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.buffer.Buffer;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.CursorInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Paint;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Update;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.Tool;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 *   @author indy
 */
public class Painting implements PaintingInterface, Paint {

    protected CommandExecutor executor = null;
    protected CursorInterface cursorInterface = null;
    protected Update update = null;
    protected int layerID = -1;
    protected Buffer buffer = null;
    protected Tool tool = null;
    protected int color = Color.GREEN.getRGB();

    public Painting(CommandExecutor executor, CursorInterface cursorInterface, Update update) {
        this.executor = executor;
        this.cursorInterface = cursorInterface;
        this.update = update;
        buffer = new Buffer(executor);
    }

    @Override
    public void paint(PaintData paintData) {
        if (layerID >= 0) {
            ArrayList<PaintImage> paintImages = paintData.getPaintImages();
            for (PaintImage paintImage : paintImages) {
                BufferedImage image = paintImage.getPaintImage();
                ArrayList<Point> points = paintImage.getPoints();
                for (Point point : points) {
                    if (paintImage.isAdd()) {
                        buffer.paintAdd(image, point.x, point.y);
                    } else {
                        buffer.paintRemove(image, point.x, point.y);
                    }
                    update.update(point.x, point.y, image.getWidth(), image.getHeight());
                }
            }
            update.repaint();
        }
    }

    public BufferedImage addLocalChanges(BufferedImage source, int x, int y, int width, int height) {
        return buffer.addLocalChanges(source, x, y, width, height);
    }

    public void addReceived(int identificator) {
        buffer.addReceived(identificator);
    }

    public void removeReceived(int identificator) {
        buffer.removeReceived(identificator);
    }

    public void dismiss() {
        buffer.dismiss();
    }

    public void noLayer() {
        layerID = -1;
        buffer.noLayer();
    }

    public void setLayer(int layerID, int width, int height) {
        this.layerID = layerID;
        buffer.setLayer(layerID, width, height);
    }

    public int getLayerID() {
        return layerID;
    }

    public void mousePressed(int x, int y, boolean shift, boolean control) {
        if (tool != null) {
            tool.mousePressed(x, y, shift, control);
        }
    }

    public void mouseDragged(int x, int y, boolean shift, boolean control) {
        if (tool != null) {
            tool.mouseDragged(x, y, shift, control);
        }
    }

    public void mouseMoved(int x, int y, boolean shift, boolean control) {
        if (tool != null) {
            tool.mouseMoved(x, y, shift, control);
        }
    }

    public void mouseReleased(int x, int y, boolean shift, boolean control) {
        if (tool != null) {
            tool.mouseReleased(x, y, shift, control);
        }
    }

    public void mouseWheeled(int amount, boolean shift, boolean control) {
        if (tool != null) {
            tool.mouseWheeled(amount, shift, control);
        }
    }

    public void keyPressed(int key) {
        if (tool != null) {
            tool.keyPressed(key);
        }
    }

    public void keyReleased(int key) {
        if (tool != null) {
            tool.keyReleased(key);
        }
    }

    public Tool.ToolImage getToolImage() {
        if (tool == null) {
            return null;
        }
        return tool.getToolImage();
    }

    @Override
    public void setColor(int color) {
        if (tool != null) {
            tool.setColor(color);
        }
        this.color = color;
    }

    @Override
    public void setPaintingLayer(int layer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setTool(Tool tool) {
        if (this.tool != null) {
            this.tool.setPaint(null);
        }
        this.tool = tool;
        if (tool != null) {
            tool.setPaint(this);
            tool.setColor(color);
            cursorInterface.setToolCursorIcon(tool.getToolCursor(), tool.getToolName());
        }
    }

    @Override
    public void setCursor(BufferedImage cursor) {
        cursorInterface.setBrushCursor(cursor);
    }

    @Override
    public void repaintToolImage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int pickColor(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPaintingWidth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getPaintingHeight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public BufferedImage getPaintingImage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
