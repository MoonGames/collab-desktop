/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool.tools;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.gui.desk.paintengine.tool.Tool;
import cz.mgn.collabdesktop.utils.ImageUtil;
import javax.swing.JPanel;

/**
 *
 *   @author indy
 */
public class PictureTool extends Tool {

    public PictureTool() {
        super();
        //FIXME: write description
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/picture-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/picture-icon.png"), "Picture", "...");
    }
    
    public void mousePressed(int x, int y) {
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
    public void canvasInterfaceSeted() {
        canvasInterface.getVisible().setToolCursor(null);
    }

    @Override
    public void canvasInterfaceUnset() {
    }

    @Override
    public void mouseEvent(CollabPanelMouseEvent e) {
        if(e.getEventType() == CollabPanelMouseEvent.TYPE_PRESS) {
            mousePressed(e.getEventCoordinates().x, e.getEventCoordinates().y);
        }
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }
}
