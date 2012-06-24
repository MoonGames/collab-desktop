/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.latex;

import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Paint;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.GraphicsUtil;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *        @author indy
 */
public class LatexTool extends Tool implements LatexImageListener {

    protected int x = -1;
    protected int y = -1;
    protected BufferedImage latexImage = null;
    protected BufferedImage toolImage = null;
    protected LatexPanel toolPanel = null;

    public LatexTool(ForToolInterface forToolInterface) {
        super(forToolInterface);
        init(ImageUtil.loadImageFromResources("/resources/tools/latex-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/latex-icon.png"), "LaTeX", "Render, than press to draw.");

        toolPanel = new LatexPanel(this);
    }

    @Override
    public void paintSeted() {
        paint.setCursor(null);
    }

    @Override
    public void mousePressed(int x, int y, boolean shift, boolean control) {
        if (latexImage != null) {
            paint.paint(new Paint.PaintData(new Paint.PaintImage(true, latexImage, new Point(x - (latexImage.getWidth() / 2), y - (latexImage.getHeight() / 2)))));
        }
    }

    @Override
    public void mouseDragged(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void mouseMoved(int x, int y, boolean shift, boolean control) {
        this.x = x;
        this.y = y;
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
        toolPanel.setColor(color);
    }

    @Override
    public ToolImage getToolImage() {
        if (toolImage != null && x >= 0 && y >= 0) {
            return new ToolImage(x - (toolImage.getWidth() / 2), y - (toolImage.getHeight() / 2), toolImage);
        }
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
    public void setLatexImage(BufferedImage image) {
        latexImage = image;
        toolImage = GraphicsUtil.generateOutline(image, Color.GRAY, true);
    }
}
