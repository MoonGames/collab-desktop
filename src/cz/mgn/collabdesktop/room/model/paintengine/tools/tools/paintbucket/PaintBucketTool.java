/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.paintbucket;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineInterface;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.paintdata.SimplePaintData;
import cz.mgn.collabdesktop.room.model.paintengine.tools.paintdata.SingleImagePaintData;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.ToolsUtils;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class PaintBucketTool extends Tool {

    protected PaintBucketPanel toolPanel;
    protected float tolerance = 0f;
    protected int color = 0xff000000;
    protected Canvas canvas = null;
    protected BufferedImage toolIcon;

    public PaintBucketTool() {
        toolIcon = ImageUtil.loadImageFromResources("/resources/tools/paintbucket-icon.png");
        toolPanel = new PaintBucketPanel();
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
            BufferedImage over = canvas.getPaintable().getSelectedLayerImage(null);
            if (e.getEventCoordinates().x >= 0
                    && e.getEventCoordinates().y >= 0
                    && e.getEventCoordinates().x < over.getWidth()
                    && e.getEventCoordinates().y < over.getHeight()) {
                BufferedImage generate = null;
                int localColor = color;
                generate = new BufferedImage(over.getWidth(), over.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
                FloodFill fill = new FloodFill(over, tolerance);
                BufferedImage gen = fill.fill(e.getEventCoordinates().x, e.getEventCoordinates().y, localColor);
                Graphics g = generate.getGraphics();
                g.drawImage(gen, 0, 0, null);
                g.dispose();
                canvas.getPaintable().paint(new SingleImagePaintData(generate, !e.isControlDown()));
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
        return "Paint bucket";
    }

    @Override
    public String getToolDescription() {
        return "Click to fill area, press CTRL for earsing.";
    }

    @Override
    public JPanel getToolPanel() {
        return toolPanel;
    }
}
