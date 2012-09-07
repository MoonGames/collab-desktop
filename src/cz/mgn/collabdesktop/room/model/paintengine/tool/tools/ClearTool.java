/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tool.tools;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengine.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengine.tool.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tool.paintdata.SingleImagePaintData;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class ClearTool extends Tool {

    protected int color = 0;

    public ClearTool() {
        super();
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/clear-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/clear-icon.png"), "Clear", "Click to fill, with CTRL earse.");
    }

    public void mousePressed(int x, int y, boolean control) {
        int w = canvasInterface.getPaintable().getWidth();
        int h = canvasInterface.getPaintable().getHeight();
        if (control) {
            BufferedImage clear = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = clear.getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, clear.getWidth(), clear.getHeight());
            g.dispose();
            canvasInterface.getPaintable().paint(new SingleImagePaintData(new Point(0, 0), clear, false));
        } else {
            BufferedImage fill = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = fill.getGraphics();
            g.setColor(new Color(color));
            g.fillRect(0, 0, fill.getWidth(), fill.getHeight());
            g.dispose();
            canvasInterface.getPaintable().paint(new SingleImagePaintData(new Point(0, 0), fill, true));
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public ToolImage getToolImage() {
        return null;
    }

    @Override
    public JPanel getToolOptionsPanel() {
        return null;
    }

    @Override
    public void canvasInterfaceSeted() {
        canvasInterface.getVisible().setToolCursor(null);
    }

    @Override
    public void canvasInterfaceUnset() {
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        if (e.getEventType() == CollabPanelMouseEvent.TYPE_PRESS) {
            mousePressed(e.getEventCoordinates().x, e.getEventCoordinates().y, e.isControlDown());
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }
}
