/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool.tools.paintbucket;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.Tool;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.paintdata.SingleImagePaintData;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class PaintBucketTool extends Tool implements PaintBucketOptions {

    protected int color = 0;
    protected float tolerance = 0;
    protected JPanel toolPanel;

    public PaintBucketTool() {
        super();
        //FIXME: write brush description
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/paintbucket-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/paintbucket-icon.png"), "Paint bucket", "Press CTRL for erasing.");
        createToolpanel();
    }

    protected void createToolpanel() {
        toolPanel = new PaintBucketPanel(this);
    }

    public void mousePressed(int x, int y, boolean control) {
        BufferedImage over = canvasInterface.getPaintable().getSelectedLayerImage(null);
        if (x >= 0 && y >= 0 && x < over.getWidth() && y < over.getHeight()) {
            BufferedImage generate = null;
            int localColor = color;
            generate = new BufferedImage(over.getWidth(), over.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            FloodFill fill = new FloodFill(over, tolerance);
            BufferedImage gen = fill.fill(x, y, localColor);
            Graphics g = generate.getGraphics();
            g.drawImage(gen, 0, 0, null);
            g.dispose();
            canvasInterface.getPaintable().paint(new SingleImagePaintData(generate, !control));
        }
    }

    @Override
    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public JPanel getToolOptionsPanel() {
        return toolPanel;
    }

    @Override
    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;
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
            mousePressed(e.getEventCoordinates().x,
                    e.getEventCoordinates().y, e.isControlDown());
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }

    @Override
    public ToolImage getToolImage() {
        return null;
    }
}
