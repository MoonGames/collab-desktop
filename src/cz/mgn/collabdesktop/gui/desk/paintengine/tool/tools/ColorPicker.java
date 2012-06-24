/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.gui.desk.paintengine.PaintEngine;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class ColorPicker extends Tool {

    protected PaintEngine paintEngine;

    public ColorPicker(PaintEngine paintEngine) {
        super();
        this.paintEngine = paintEngine;
        //FIXME: write picker description
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/colorpicker-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/colorpicker-icon.png"), "Color picker", "...");
    }

    public void mousePressed(int x, int y) {
        if (paintEngine != null) {
            BufferedImage pickImage = canvasInterface.getPaintable().getSelectedLayerImage(new Rectangle(x, y, 1, 1));
            if (pickImage != null) {
                int color = pickImage.getRGB(0, 0);
                if (new Color(color).getAlpha() > 0) {
                    paintEngine.setColor(color);
                }
            }
        }
    }

    @Override
    public void setColor(int color) {
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
            mousePressed(e.getEventCoordinates().x, e.getEventCoordinates().y);
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }
}
