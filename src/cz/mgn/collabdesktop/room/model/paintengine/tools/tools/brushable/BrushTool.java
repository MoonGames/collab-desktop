/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tools.tools.brushable;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabdesktop.room.model.paintengine.tools.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengine.tools.tools.ToolsUtils;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 *
 * @author indy
 */
public class BrushTool extends BrushableTool {

    protected SimpleMouseCursor mouseCursor;
    protected BufferedImage toolIcon;
    protected Point lastPoint = null;

    public BrushTool() {
        super();
        init();
    }

    private void init() {
        mouseCursor = new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/brush-cursor.gif"));
        toolIcon = ImageUtil.loadImageFromResources("/resources/tools/brush-icon.png");
    }

    public void mousePressed(Point coordinates, boolean control) {
        lastPoint = new Point(coordinates.x, coordinates.y);
        paint(coordinates.x, coordinates.y, control);
    }

    public void mouseDragged(Point coordinates, boolean control) {
        if (lastPoint == null) {
            lastPoint = new Point(coordinates);
        }
        paint(coordinates.x, coordinates.y, control);
        lastPoint = new Point(coordinates);
    }

    public void mouseReleased() {
        lastPoint = null;
    }

    protected void paint(int x, int y, boolean isControlDown) {
        paintLine(lastPoint.x, lastPoint.y, x, y, !isControlDown);
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        switch (e.getEventType()) {
            case CollabPanelMouseEvent.TYPE_PRESS:
                mousePressed(e.getEventCoordinates(), e.isControlDown());
                break;
            case CollabPanelMouseEvent.TYPE_DRAG:
                mouseDragged(e.getEventCoordinates(), e.isControlDown());
                break;
            case CollabPanelMouseEvent.TYPE_RELEASE:
                mouseReleased();
                break;
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
        return "Brush";
    }

    @Override
    public String getToolDescription() {
        return "Press CTRL for erase.";
    }
}
