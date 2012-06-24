/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools.text;

import cz.mgn.collabcanvas.canvas.utils.graphics.OutlineUtil;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Paint;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 *       @author indy
 */
public class TextTool extends Tool implements TextImageListener {

    protected int x = -1;
    protected int y = -1;
    protected BufferedImage textImage = null;
    protected BufferedImage toolImage = null;
    protected TextPanel toolPanel;

    public TextTool() {
        super();
        init(ImageUtil.loadImageFromResources("/resources/tools/text-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/text-icon.png"), "Text", "Press to draw text.");
        
        toolPanel = new TextPanel(this);
    }

    @Override
    public void paintSeted() {
        paint.setCursor(null);
    }

    @Override
    public void mouseMoved(int x, int y, boolean shift, boolean control) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void mousePressed(int x, int y, boolean shift, boolean control) {
        if (textImage != null) {
            paint.paint(new Paint.PaintData(new Paint.PaintImage(true, textImage, new Point(x - (textImage.getWidth() / 2), y - (textImage.getHeight() / 2)))));
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
        toolPanel.setColor(new Color(color));
    }

    @Override
    public ToolImage getToolImage() {
        if (toolImage != null) {
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
    public void textRendered(BufferedImage textImage) {
        this.textImage = textImage;
        toolImage = OutlineUtil.generateOutline(textImage, Color.GRAY, true);
    }
}
