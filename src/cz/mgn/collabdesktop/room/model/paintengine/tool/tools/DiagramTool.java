/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengine.tool.tools;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengine.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.model.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public class DiagramTool extends Tool {

    public DiagramTool() {
        super();
        //FIXME: write diagram tool description
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/diagram-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/diagram-icon.png"), "Diagram", "...");
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
