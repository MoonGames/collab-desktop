/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.paintbucket;

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
 * @author indy
 */
public class PaintBucketTool extends Tool implements PaintBucketOptions {

    protected int color = 0;
    protected float tolerance = 0;
    protected JPanel toolPanel;

    public PaintBucketTool(ForToolInterface forToolInterface) {
        super(forToolInterface);
        //FIXME: write brush description
        init(ImageUtil.loadImageFromResources("/resources/tools/paintbucket-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/paintbucket-icon.png"), "Paint bucket", "Press CTRL for erasing.");
        createToolpanel();
    }

    @Override
    public void mouseMoved(int x, int y, boolean shift, boolean control) {
    }

    protected void createToolpanel() {
        toolPanel = new PaintBucketPanel(this);
    }

    @Override
    public void paintSeted() {
        paint.setCursor(null);
    }

    @Override
    public void mousePressed(int x, int y, boolean shift, boolean control) {
        BufferedImage over = forToolInterface.getPaintingImage();
        if (x >= 0 && y >= 0 && x < over.getWidth() && y < over.getHeight()) {
            BufferedImage generate = null;
            int localColor = color;
            if (control) {
                localColor = 0;
                generate = new BufferedImage(over.getWidth(), over.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
                Graphics g = generate.getGraphics();
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, over.getWidth(), over.getHeight());
                g.dispose();
            } else {
                generate = new BufferedImage(over.getWidth(), over.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            }
            FloodFill fill = new FloodFill(over, tolerance);
            BufferedImage gen = fill.fill(x, y, localColor);
            Graphics g = generate.getGraphics();
            g.drawImage(gen, 0, 0, null);
            g.dispose();
            paint.paint(new Paint.PaintData(new Paint.PaintImage(!control, generate, new Point(0, 0))));
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
        return toolPanel;
    }

    @Override
    public void paintUnset() {
    }

    @Override
    public void setTolerance(float tolerance) {
        this.tolerance = tolerance;
    }
}
