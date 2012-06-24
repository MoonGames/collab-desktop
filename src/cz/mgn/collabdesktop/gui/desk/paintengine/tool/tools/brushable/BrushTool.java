/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.brushable;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.toolspanel.extra.brushs.BrushPanel;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Paint;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.brushable.brush.Brush;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 *   @author indy
 */
public class BrushTool extends BrushableTool {

    protected Point lastPoint = null;

    public BrushTool(BrushPanel brushPanel) {
        super(brushPanel);

        //FIXME: write brush description
        init(ImageUtil.loadImageFromResources("/resources/tools/brush-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/brush-icon.png"), "Brush", "Press CTRL for erase.");

    }

    protected void paint(int x, int y, boolean control) {
        if (paint != null) {
            Brush.PaintBrush paintBrush = brush.paintLine(lastPoint.x, lastPoint.y, x, y, !control);
            Paint.PaintData data = new Paint.PaintData(new Paint.PaintImage(!control, paintBrush.paint, paintBrush.getPoints()));
            paint.paint(data);
        }
    }

    @Override
    public void mousePressed(int x, int y, boolean shift, boolean control) {
        lastPoint = new Point(x, y);
        paint(x, y, control);
    }

    @Override
    public void mouseDragged(int x, int y, boolean shift, boolean control) {
        if (lastPoint == null) {
            lastPoint = new Point(x, y);
        }
        paint(x, y, control);
        lastPoint = new Point(x, y);
    }

    @Override
    public void mouseReleased(int x, int y, boolean shift, boolean control) {
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
    public void canvasInterfaceSeted() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void canvasInterfaceUnset() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
