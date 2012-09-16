/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.clear;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineInterface;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.paintdata.SimplePaintData;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.ToolsUtils;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class ClearTool extends Tool {

    protected BufferedImage toolIcon;
    protected int color = 0xff000000;
    protected Canvas canvas = null;

    public ClearTool() {
        toolIcon = ImageUtil.loadImageFromResources("/resources/tools/clear-icon.png");
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void setPaintEngineInterface(PaintEngineInterface paintEngineInterface) {
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        if (e.getEventType() == CollabPanelMouseEvent.TYPE_PRESS) {
            if (canvas != null) {
                int w = canvas.getPaintable().getWidth();
                int h = canvas.getPaintable().getHeight();
                ArrayList<Point> points = new ArrayList<Point>();
                points.add(new Point(0, 0));
                BufferedImage fill = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
                Graphics g = fill.getGraphics();
                if (e.isControlDown()) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(new Color(color));
                }
                g.fillRect(0, 0, fill.getWidth(), fill.getHeight());
                g.dispose();
                canvas.getPaintable().paint(new SimplePaintData(points, fill, !e.isControlDown()));
            }
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }

    @Override
    public BufferedImage getToolIcon() {
        return toolIcon;
    }

    @Override
    public BufferedImage getToolIcon(int width, int height) {
        return ToolsUtils.transformToolIcon(toolIcon, width, height);
    }

    @Override
    public String getToolName() {
        return "Clearer";
    }

    @Override
    public String getToolDescription() {
        return "Press CTRL for earsing.";
    }

    @Override
    public JPanel getToolPanel() {
        return null;
    }
}
