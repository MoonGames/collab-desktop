/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool;

import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.interfaces.Paint;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public abstract class Tool {

    protected Paint paint;
    protected ForToolInterface forToolInterface;
    //
    protected BufferedImage toolCursor;
    protected BufferedImage toolIcon;
    protected String toolName = "tool";
    protected String toolDescription = "";

    public Tool(ForToolInterface forToolInterface) {
        this.forToolInterface= forToolInterface;
    }

    public void init(BufferedImage toolCursor, BufferedImage toolIcon, String toolName, String toolDescription) {
        this.toolCursor = toolCursor;
        this.toolIcon = toolIcon;
        this.toolName = toolName;
        this.toolDescription = toolDescription;
    }

    public void setPaint(Paint paint) {
        this.paint = paint;
        if (paint != null) {
            paintSeted();
        } else {
            paintUnset();
        }
    }

    public abstract void paintSeted();
    
    public abstract void paintUnset();

    public BufferedImage getToolCursor() {
        return toolCursor;
    }

    public BufferedImage getToolIcon() {
        return toolIcon;
    }

    public String getToolName() {
        return toolName;
    }

    public String getToolDescription() {
        return toolDescription;
    }

    public abstract void mousePressed(int x, int y, boolean shift, boolean control);
    
    public abstract void mouseMoved(int x, int y, boolean shift, boolean control);

    public abstract void mouseDragged(int x, int y, boolean shift, boolean control);

    public abstract void mouseReleased(int x, int y, boolean shift, boolean control);

    public abstract void mouseWheeled(int amount, boolean shift, boolean control);

    public abstract void keyPressed(int keyCode);

    public abstract void keyReleased(int keyCode);

    public abstract void setColor(int color);

    public abstract ToolImage getToolImage();
    
    public abstract JPanel getToolOptionsPanel();

    public class ToolImage {

        protected int x = 0;
        protected int y = 0;
        protected BufferedImage image = null;

        public ToolImage(int x, int y, BufferedImage image) {
            this.x = x;
            this.y = y;
            this.image = image;
        }
        
        public void setCoords(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public BufferedImage getImage() {
            return image;
        }
    }
}
