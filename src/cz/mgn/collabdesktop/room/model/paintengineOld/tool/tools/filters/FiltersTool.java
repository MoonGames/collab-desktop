/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool.tools.filters;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import javax.swing.JPanel;

/**
 *
 *     @author indy
 */
public class FiltersTool extends Tool {

    protected int color = 0;

    public FiltersTool() {
        super();
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/clear-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/clear-icon.png"), "Clear", "Click to use.");
    }

    @Override
    public void canvasInterfaceSeted() {
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
}
