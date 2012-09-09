/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool.tools.brushable;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.paintable.PaintData;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.paintdata.SimplePaintData;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.tools.brushable.brush.Brush;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.toolspanel.extra.brushs.BrushPanel;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class BrushTool extends BrushableTool {

    protected Point lastPoint = null;

    public BrushTool(BrushPanel brushPanel) {
        super(brushPanel);
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/brush-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/brush-icon.png"), "Brush", "Press CTRL for erase.");

    }

    protected void paint(int x, int y, boolean control) {
        if (canvasInterface != null) {
            Brush.PaintBrush paintBrush = brush.paintLine(lastPoint.x, lastPoint.y, x, y);
            PaintData data = new SimplePaintData(paintBrush.getPoints(), paintBrush.paint, !control);

            canvasInterface.getPaintable().paint(data);
        }
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        super.mouseEvent(e);
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

    @Override
    public ToolImage getToolImage() {
        return null;
    }

    @Override
    public JPanel getAditionalPanel() {
        return null;
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }
}
