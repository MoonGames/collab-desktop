/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.mgn.collabdesktop.gui.desk.paintengine.tool;

import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelKeyEvent;
import cz.mgn.collabcanvas.interfaces.listenable.CollabPanelMouseEvent;
import cz.mgn.collabcanvas.interfaces.visible.MouseCursor;
import cz.mgn.collabcanvas.interfaces.visible.ToolImage;
import cz.mgn.collabdesktop.gui.desk.paintengine.PaintEngineInterface;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author indy
 */
public abstract class Tool {

    protected CanvasInterface canvasInterface;
    protected PaintEngineInterface paintEngineInterface;
    //
    protected MouseCursor toolCursor;
    protected BufferedImage toolIcon;
    protected String toolName = "tool";
    protected String toolDescription = "";

    public Tool() {
    }

    public void init(MouseCursor toolCursor, BufferedImage toolIcon, String toolName, String toolDescription) {
        this.toolCursor = toolCursor;
        this.toolIcon = toolIcon;
        this.toolName = toolName;
        this.toolDescription = toolDescription;
    }

    public void setCanvsaInterface(CanvasInterface canvasInterface) {
        this.canvasInterface = canvasInterface;
        if (canvasInterface != null) {
            canvasInterfaceSeted();
        } else {
            canvasInterfaceUnset();
        }
    }
    
    public void setPaintEngineInterface(PaintEngineInterface paintEngineInterface) {
        this.paintEngineInterface = paintEngineInterface;
    }

    public abstract void canvasInterfaceSeted();

    public abstract void canvasInterfaceUnset();

    public MouseCursor getToolCursor() {
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
