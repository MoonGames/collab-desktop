/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools;

import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import javax.swing.JPanel;

/**
 *
 *   @author indy
 */
public class DiagramTool extends Tool {

    public DiagramTool() {
        super();
        //FIXME: write brush description
        init(ImageUtil.loadImageFromResources("/resources/tools/diagram-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/diagram-icon.png"), "Diagram", "...");
    }

    @Override
    public void paintSeted() {
        paint.setCursor(null);
    }

    @Override
    public void mouseMoved(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void mousePressed(int x, int y, boolean shift, boolean control) {
        int color = forToolInterface.pickColor(x, y);
        if (color != -1) {
            forToolInterface.setColor(color);
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
