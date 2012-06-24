/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools;

import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Paint;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *    @author indy
 */
public class ClearTool extends Tool {

    protected int color = 0;

    public ClearTool(ForToolInterface forToolInterface) {
        super(forToolInterface);
        //FIXME: write description
        init(ImageUtil.loadImageFromResources("/resources/tools/clear-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/clear-icon.png"), "Clear", "Click to fill, with CTRL clear.");
    }

    @Override
    public void mouseMoved(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void paintSeted() {
        paint.setCursor(null);
    }

    @Override
    public void mousePressed(int x, int y, boolean shift, boolean control) {
        if (control) {
            BufferedImage clear = new BufferedImage(forToolInterface.getPaintingWidth(), forToolInterface.getPaintingHeight(), BufferedImage.TYPE_BYTE_GRAY);
            Graphics g = clear.getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, clear.getWidth(), clear.getHeight());
            g.dispose();
            paint.paint(new Paint.PaintData(new Paint.PaintImage(false, clear, new Point(0, 0))));
        } else {
            BufferedImage fill = new BufferedImage(forToolInterface.getPaintingWidth(), forToolInterface.getPaintingHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics g = fill.getGraphics();
            g.setColor(new Color(color));
            g.fillRect(0, 0, fill.getWidth(), fill.getHeight());
            g.dispose();
            paint.paint(new Paint.PaintData(new Paint.PaintImage(true, fill, new Point(0, 0))));
        }
    }

    @Override
    public void mouseDragged(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void mouseReleased(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void mouseWheeled(int amount, boolean shift, boolean control) {
    }

    @Override
    public void keyPressed(int keyCode) {
    }

    @Override
    public void keyReleased(int keyCode) {
    }

    @Override
    public void setColor(int color) {
        this.color = color;
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
    public void paintUnset() {
    }
}
