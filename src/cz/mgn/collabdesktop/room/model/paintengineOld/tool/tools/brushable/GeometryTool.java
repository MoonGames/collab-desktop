/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.room.model.paintengineOld.tool.tools.brushable;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.room.model.paintengineOld.tool.SimpleMouseCursor;
import cz.mgn.collabdesktop.room.view.panels.leftpanel.toolspanel.extra.brushs.BrushPanel;
import cz.mgn.collabdesktop.utils.ImageUtil;
import javax.swing.JPanel;

/**
 *
 *  @author indy
 */
public class GeometryTool extends BrushableTool {

    public GeometryTool(BrushPanel brushPanel) {
        super(brushPanel);
        //TODO: write description
        init(new SimpleMouseCursor(ImageUtil.loadImageFromResources("/resources/tools/geometry-cursor.gif")),
                ImageUtil.loadImageFromResources("/resources/tools/geometry-icon.png"), "Geometry", "...");
    }

    @Override
    public JPanel getAditionalPanel() {
        return null;
    }

    @Override
    public void keyEvent(CollabPanelKeyEvent e) {
    }

    @Override
    public ToolImage getToolImage() {
        return null;
    }
}
