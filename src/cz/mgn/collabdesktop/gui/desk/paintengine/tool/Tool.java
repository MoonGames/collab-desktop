/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.paintable.Paintable;
import cz.mgn.collabcanvas.interfaces.selectionable.Selectionable;
import cz.mgn.collabcanvas.interfaces.visible.ToolCursor;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public abstract class Tool {

    protected CanvasInterface canvasInterface;
    //
    protected ToolCursor toolCursor;
    protected BufferedImage toolIcon;
    protected String toolName = "tool";
    protected String toolDescription = "";

    public Tool() {
    }

    public void init(ToolCursor toolCursor, BufferedImage toolIcon, String toolName, String toolDescription) {
        this.toolCursor = toolCursor;
        this.toolIcon = toolIcon;
        this.toolName = toolName;
        this.toolDescription = toolDescription;
    }

    public void setPaint(CanvasInterface canvasInterface) {
        this.canvasInterface = canvasInterface;
        if (canvasInterface != null) {
            canvasInterfaceSeted();
        } else {
            canvasInterfaceUnset();
        }
    }

    public abstract void canvasInterfaceSeted();

    public abstract void canvasInterfaceUnset();

    public ToolCursor getToolCursor() {
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

    public abstract void mouseEvent(CollabPanelMouseEvent e);

    public abstract void keyEvent(CollabPanelKeyEvent e);

    public abstract void setColor(int color);

    public abstract ToolImage getToolImage();

    public abstract JPanel getToolOptionsPanel();
}
