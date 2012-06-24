/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.panels.middlepanel.paintengine.tool.tools.brushable;

import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.ForToolInterface;
import cz.mgn.collabdesktop.gui.desk.panels.leftpanel.toolspanel.extra.brushs.BrushPanel;
import cz.mgn.collabdesktop.utils.ImageUtil;
import javax.swing.JPanel;

/**
 *
 *  @author indy
 */
public class GeometryTool extends BrushableTool {

    public GeometryTool(ForToolInterface forToolInterface, BrushPanel brushPanel) {
        super(forToolInterface, brushPanel);
        //FIXME: write description
        init(ImageUtil.loadImageFromResources("/resources/tools/geometry-cursor.gif"),
                ImageUtil.loadImageFromResources("/resources/tools/geometry-icon.png"), "Geometry", "...");
    }

    @Override
    public JPanel getAditionalPanel() {
        return null;
    }

    @Override
    public void mouseMoved(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void mousePressed(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void mouseDragged(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void mouseReleased(int x, int y, boolean shift, boolean control) {
    }

    @Override
    public void keyPressed(int keyCode) {
    }

    @Override
    public void keyReleased(int keyCode) {
    }

    @Override
    public ToolImage getToolImage() {
        return null;
    }

    @Override
    public void paintUnset() {
    }
}
