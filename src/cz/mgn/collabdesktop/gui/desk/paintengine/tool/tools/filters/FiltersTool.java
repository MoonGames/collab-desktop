/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.filters;

import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Paint;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.Tool;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.filters.filters.GaussianFilter;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *     @author indy
 */
public class FiltersTool extends Tool {

    protected int color = 0;

    public FiltersTool() {
        super();
        //FIXME: all
        init(ImageUtil.loadImageFromResources("/resources/tools/clear-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/clear-icon.png"), "Clear", "Click to use.");
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
        BufferedImage img = forToolInterface.getPaintingImage();
//        GaussianFilter gf = new GaussianFilter(5);
//        img = gf.blur(img);
        BufferedImage black = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = black.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, black.getWidth(), black.getHeight());
        g.dispose();
       // paint.paint(new Paint.PaintData(new Paint.PaintImage(false, black, new Point(0, 0))));
        paint.paint(new Paint.PaintData(new Paint.PaintImage(true, img, new Point(0, 0))));
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
