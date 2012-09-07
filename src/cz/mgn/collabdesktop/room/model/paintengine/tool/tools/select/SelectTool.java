/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tool.tools.select;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.selectionable.SelectionUpdate;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengine.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class SelectTool extends Tool implements SelectPaneInterface {

    protected int x1 = -1;
    protected int y1 = -1;
    protected int x2 = -1;
    protected int y2 = -1;
    //
    protected SelectPanel toolPanel;

    public SelectTool() {
        super();
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/select-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/select-icon.png"), "Select", "Press CTRL and click to select all.");
        toolPanel = new SelectPanel(this);
    }

    public void mousePressed(int x, int y, boolean control) {
        if (control) {
            reset();
            canvasInterface.getSelectionable().selectAll();
        } else {
            x1 = x;
            y1 = y;
            set(x, y);
        }
    }

    public void mouseDragged(int x, int y) {
        set(x, y);
    }

    public void mouseReleased(int x, int y) {
        if (x1 >= 0) {
            Rectangle r = countRect();
            BufferedImage selection = new BufferedImage(r.width, r.height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = selection.getGraphics();
            g.setColor(Color.BLACK);
            if (toolPanel.getSelectionShape() == SelectPanel.SHAPE_OVAL) {
                g.fillOval(0, 0, selection.getWidth(), selection.getHeight());
            } else {
                g.fillRect(0, 0, selection.getWidth(), selection.getHeight());
            }
            canvasInterface.getSelectionable().select(new ToolSelectionUpdate(toolPanel.getSelectionType(), new Point(r.x, r.y), 1f, selection));
        }
        reset();
    }

    protected Rectangle countRect() {
        int xx1 = Math.max(0, Math.min(x1, x2));
        int xx2 = Math.min(Math.max(x1, x2), canvasInterface.getPaintable().getWidth());
        if (xx1 == xx2) {
            xx2++;
        }
        int yy1 = Math.max(0, Math.min(y1, y2));
        int yy2 = Math.min(Math.max(y1, y2), canvasInterface.getPaintable().getHeight());
        if (yy1 == yy2) {
            yy2++;
        }
        return new Rectangle(xx1, yy1, xx2 - xx1, yy2 - yy1);
    }

    protected void set(int x, int y) {
        x2 = x;
        y2 = y;
    }

    protected void reset() {
        x1 = -1;
        x2 = -1;
        y1 = -1;
        y2 = -1;
    }

    @Override
    public void setColor(int color) {
    }

    @Override
    public ToolImage getToolImage() {
        if (x1 >= 0) {
            Rectangle r = countRect();
            BufferedImage ti = new BufferedImage(r.width, r.height, BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g = (Graphics2D) ti.getGraphics();
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(1.0f,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, new float[]{10f}, 0.0f));
            if (toolPanel.getSelectionShape() == SelectPanel.SHAPE_OVAL) {
                g.drawOval(0, 0, ti.getWidth() - 1, ti.getHeight() - 1);
            } else {
                g.drawRect(0, 0, ti.getWidth() - 1, ti.getHeight() - 1);
            }
            g.dispose();
            //TODO:
            //return new ToolImage(r.x, r.y, ti);
        }
        return null;
    }

    @Override
    public JPanel getToolOptionsPanel() {
        return toolPanel;
    }

    @Override
    public void invertSelection() {
        BufferedImage selection = new BufferedImage(canvasInterface.getPaintable().getWidth(),
                canvasInterface.getPaintable().getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = selection.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, selection.getWidth(), selection.getHeight());
        canvasInterface.getSelectionable().select(new ToolSelectionUpdate(SelectionUpdate.MODE_XOR,
                1f, selection));
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
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }
}
