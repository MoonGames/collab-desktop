/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tool.tools.latex;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengine.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengine.tool.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tool.paintdata.SingleImagePaintData;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class LatexTool extends Tool implements LatexImageListener {

    protected int x = -1;
    protected int y = -1;
    protected BufferedImage latexImage = null;
    protected BufferedImage toolImage = null;
    protected LatexPanel toolPanel = null;

    public LatexTool() {
        super();
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/latex-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/latex-icon.png"), "LaTeX", "Render, than press to draw.");
        toolPanel = new LatexPanel(this);
    }

    public void mousePressed(int x, int y) {
        if (latexImage != null) {
            canvasInterface.getPaintable().paint(new SingleImagePaintData(new Point(x - (latexImage.getWidth() / 2), y - (latexImage.getHeight() / 2)), latexImage, true));
        }
    }

    public void mouseMoved(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setColor(int color) {
        toolPanel.setColor(color);
    }

    @Override
    public ToolImage getToolImage() {
        if (toolImage != null) {
            //TODO:
            //return new ToolImage(x - (toolImage.getWidth() / 2), y - (toolImage.getHeight() / 2), toolImage);
        }
        return null;
    }

    @Override
    public JPanel getToolOptionsPanel() {
        return toolPanel;
    }

    @Override
    public void setLatexImage(BufferedImage image) {
        latexImage = image;
        toolImage = OutlineUtil.generateOutline(image, Color.GRAY, true);
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
        switch (e.getEventType()) {
            case CollabPanelMouseEvent.TYPE_PRESS:
                mousePressed(e.getEventCoordinates().x, e.getEventCoordinates().y);
                break;
            case CollabPanelMouseEvent.TYPE_MOVE:
                mouseMoved(e.getEventCoordinates().x, e.getEventCoordinates().y);
                break;
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }
}
