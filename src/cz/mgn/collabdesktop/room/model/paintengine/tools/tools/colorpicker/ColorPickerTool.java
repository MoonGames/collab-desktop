/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.colorpicker;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.Canvas;
import cz.mgn.collabdesktop.room.model.paintengine.PaintEngineInterface;
import cz.mgn.collabdesktop.room.model.paintengine.tools.Tool;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.ToolsUtils;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class ColorPickerTool extends Tool {

    protected Canvas canvas = null;
    protected PaintEngineInterface paintEngineInterface = null;
    protected BufferedImage toolIcon;

    public ColorPickerTool() {
        toolIcon = ImageUtil.loadImageFromResources("/resources/tools/colorpicker-icon.png");
    }

    public void mousePressed(int x, int y) {
        if (canvas != null && paintEngineInterface != null) {
            BufferedImage pickImage = canvas.getPaintable().getSelectedLayerImage(new Rectangle(x, y, 1, 1));
            if (pickImage != null) {
                int color = pickImage.getRGB(0, 0);
                if (((color & 0xff000000) >>> 24) > 0) {
                    paintEngineInterface.setColor(color);
                }
            }
        }
    }

    @Override
    public void setColor(int color) {
    }

    @Override
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    @Override
    public void setPaintEngineInterface(PaintEngineInterface paintEngineInterface) {
        this.paintEngineInterface = paintEngineInterface;
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
        return "Color picker";
    }

    @Override
    public String getToolDescription() {
        return "Click to canvas to pick color.";
    }

    @Override
    public JPanel getToolPanel() {
        return null;
    }
}
